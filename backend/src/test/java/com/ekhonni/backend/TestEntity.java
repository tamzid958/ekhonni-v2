package com.ekhonni.backend;

/**
 * Author: Asif Iqbal
 * Date: 12/17/24
 */
class TestEntity {
    private Long id;
    private String name;

    public TestEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getName() { return name; }
}

