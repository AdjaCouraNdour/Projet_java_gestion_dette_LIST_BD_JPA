package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.Details;
import com.ism.data.repository.interfaces.DetailsRepositoryI;
import com.ism.data.services.interfaces.DetailsServiceI;

public class DetailsService implements DetailsServiceI {

    DetailsRepositoryI repo;

	public DetailsService(DetailsRepositoryI repo) {
		this.repo = repo;
	}

	@Override
	public boolean save(Details object) {
        return repo.insert(object);
	}

	@Override
	public List<Details> show() {
        return repo.selectAll();

	}

    @Override
    public void effacer(Details object) {
        repo.remove(object);
    }

    @Override
    public Details getById(int id) {
        return repo.selectById(id);
        // repo.selectAll().stream()
        //     .filter(detail -> detail.getId()==id)
        //     .findFirst()
        //     .orElse(null);    
        }
    
}
