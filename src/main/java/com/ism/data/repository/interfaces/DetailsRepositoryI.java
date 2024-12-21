package com.ism.data.repository.interfaces;

import java.util.List;

import com.ism.core.Repository.Repository;
import com.ism.data.entities.Details;

public interface DetailsRepositoryI extends Repository<Details> {
    List<Details> selectByDette(int detteId);
}
