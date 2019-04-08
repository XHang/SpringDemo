package com.springdata.bean;

import javax.persistence.*;

@Entity
@Table(name = "cxh_char_test")
public class CharTest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "char_field")
    private String charField;

    @Column(name = "varchar_field")
    private String varCharField;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCharField() {
        return charField;
    }

    public void setCharField(String charField) {
        this.charField = charField;
    }

    public String getVarCharField() {
        return varCharField;
    }

    public void setVarCharField(String varCharField) {
        this.varCharField = varCharField;
    }
}
