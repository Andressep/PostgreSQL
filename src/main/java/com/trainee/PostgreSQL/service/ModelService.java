package com.trainee.PostgreSQL.service;

import com.trainee.PostgreSQL.models.Model;
import com.trainee.PostgreSQL.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    private ModelRepository repository;

    // CREATE
    @Transactional
    public Model createModel(Model model) {
        return repository.save(model);
    }
    // READ
    @Transactional( readOnly = true )
    public List<Model> listModels() {
        return (List<Model>) repository.findAll();
    }

    @Transactional( readOnly = true )
    public Model oneModel(Integer id) {
        return repository.findById(id).orElse(null);
    }
    /*
    * Es una combinacion del metodo fiendById y el Create.
    * */

    // DELETE
    @Transactional
    public void deleteModel(Integer id) {
        repository.deleteById(id);
    }
}
