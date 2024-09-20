package com.crm;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class WhyDataIsEvilTest {
    /**
     * https://www.baeldung.com/java-hashcode
     *
     * Kolekcje w Java: Lists ({@link java.util.LinkedList}, {@link java.util.ArrayList}, {@link HashSet}, {@link java.util.HashMap}
     */

    @Test
    void test1() {
        var mySet = new HashSet<Example>();
        var element = new Example("AAA");
        mySet.add(element);

        element.setFieldA("BBB");
        mySet.add(element);

        element.setFieldA("CCC");
        mySet.add(element);

        assertThat(mySet.size()).isEqualTo(1);
    }

    @Test
    void test2() {
        var mySet = new HashSet<Example>();
        var element = new Example("AAA");
        mySet.add(element);
        mySet.add(element);
        mySet.add(element);

        assertThat(mySet.size()).isEqualTo(1);
    }

    //    @Data
    @AllArgsConstructor
    class Example {
        private String fieldA;

        public String getFieldA() {
            return fieldA;
        }

        public void setFieldA(String fieldA) {
            this.fieldA = fieldA;
        }
    }


    class MyTableA {
        @ManyToOne // https://www.baeldung.com/jpa-hibernate-associations#1-one-to-many-bidirectional-association
        private MyTableB realRelation; // FK jest tutaj
    }

    class MyTableB {
        @OneToMany()
        private MyTableA realRelation;

    }
}
