package com.cuonglmptit.SpringServer.model.filedata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface FileDataRepository extends CrudRepository<FileData, Integer> {
    FileData findByName(String name);
}
