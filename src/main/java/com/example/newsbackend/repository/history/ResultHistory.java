package com.example.newsbackend.repository.history;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ResultHistory {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "resultHistory")
    private List<StorageResult> storageResults;


    public List<StorageResult> getStorageResults() {
        return storageResults;
    }

    public void setStorageResults(List<StorageResult> storageResults) {
        this.storageResults = storageResults;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
