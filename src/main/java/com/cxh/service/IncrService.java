package com.cxh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

/**
 * 序列生成服务
 * 使用数据库。使用悲观锁和乐观锁来保证原子性
 * 在使用这项服务之前，请务必确保已经生成对应的序列表和序列字段。
 * 并且至少有一个记录
 */
@Service
public class IncrService {

    /**
     * 用于生成的序列表名
     */
    private static final String tableName = "sequence";

    /**
     * 用于生成序列的字段名
     */
    private static final String fieldName = "seq";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    /**
     * 使用数据库的sequence递增操作进行。主要思路如下
     * 每次需要递增序列时，就取出序列，然后递增序列，更新数据库。
     * 使用乐观锁思路，每次更新数据库的时候，就查看数据库的序列值和内存的序列值是不是匹配，如果不匹配。
     * 则认为有其他线程读了此序列，无法保证序列的唯一性。
     * 因此要重新执行一遍操作。
     * 需要加事务
     * @param  maxBit 序列最大的个数，超出此格式序列归0，并返回序列0
     * @return int 值，如果值为-1，则认为记录被占用，需要retry
     */
    private  int useOptimismIncreasedForInner(int maxBit) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        //悲观锁的事务要PROPAGATION_REQUIRES_NEW(强制开启一个新的事务)，防止锁定的时间太长，导致后来者取不到序列
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        Integer seq = transactionTemplate.execute((status)->{
            Map<String, Object> map = jdbcTemplate.queryForMap("select * from " + tableName);
            Integer seqResult = (Integer) map.get(fieldName);
            //序列自增1
            Integer nextSeq = seqResult + 1;
            int seqLength = nextSeq.toString().length();
            if (seqLength > maxBit) {
                nextSeq = 0;
            }
            String updateSql = String.format("update  %s set %s=%d where %s = %d", tableName, fieldName, nextSeq, fieldName, seqResult);
            int row = jdbcTemplate.update(updateSql);
            //如果本次更新匹配不上版本号，则返回-1，告诉调用方retry
            if (row == 0) {
                seqResult = -1;
            }
            return seqResult;
        });
        return seq;
    }

    /**
     * 使用乐观锁获取自增值
     * @return
     */
    public  int useOptimismIncreased(){
        int seq = -1;
        //取到最大8位的自增序列
        while ((seq = useOptimismIncreasedForInner(3)) == -1){
        }
        return seq;
    }


    /**
     * 使用悲观锁的增加
     * @return
     */
    public  int usePessimisticLockIncreased(int bit) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        //悲观锁的事务要PROPAGATION_REQUIRES_NEW，防止锁定的时间太长
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        int seq = transactionTemplate.execute((status -> {
            //for update 是行级锁和表级锁，根据where条件而定 ，这个是表级锁
            Map<String, Object> map = jdbcTemplate.queryForMap("select * from " + tableName +" for update ");
            Integer resultSeq = (Integer) map.get(fieldName);
            Integer nextSeq = resultSeq + 1;
            int seqLength = nextSeq.toString().length();
            if (seqLength > bit) {
                nextSeq = 0;
            }
            String updateSql = String.format("update  %s set %s=%d where %s = %d", tableName, fieldName, nextSeq, fieldName, resultSeq);
            jdbcTemplate.update(updateSql);
            return resultSeq;
        }));
        //事务执行完毕，自动释放锁
        return seq;
    }
}
