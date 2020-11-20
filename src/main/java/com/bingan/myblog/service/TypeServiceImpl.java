package com.bingan.myblog.service;

import com.bingan.myblog.dao.TypeRepository;
import com.bingan.myblog.entity.Type;
import com.bingan.myblog.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypesService{

    @Autowired
    TypeRepository typeRepository;

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    @Transactional
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).get();

        if(t == null){
            throw new NotFoundException("This type does not exist");
        }
        BeanUtils.copyProperties(type,t);

        return typeRepository.save(t);
    }

    @Override
    @Transactional
    public void removeType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0,size, sort);
        return typeRepository.findTop(pageable);
    }
}
