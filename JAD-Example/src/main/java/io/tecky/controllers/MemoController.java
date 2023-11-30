package io.tecky.controllers;


import io.tecky.dtos.MemoContent;
import io.tecky.forms.MemoForm;
import io.tecky.models.Memo;
import io.tecky.repositories.MemoRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

//@CrossOrigin(allowedHeaders = "*")
@RestController
public class MemoController {

    @Autowired
    MemoRepository memoRepository;

    @RequestMapping(value = "/memos/userId/{id}", method = RequestMethod.GET)
    public List<MemoContent> getMemoByUsers(@PathVariable int id ){
        var memoContents = memoRepository.findMemoContentsForUser(id);
        return memoContents;
    }

    @RequestMapping(value = "/memos",method = RequestMethod.GET)
    public List<Memo> getMemos(@RequestParam(value = "content",required = false) String content){
        if(content != null){
            return memoRepository.findByContent(content);
        }else{
            return memoRepository.findAll();
        }
    }

    @RequestMapping(value = "/memos/{userId}", method = RequestMethod.POST)

    public int createMemo(@PathVariable int userId,@RequestBody MemoForm memoForm){
        var newMemo = memoForm.createMemo();
        newMemo.setUserId(userId);
        int id = memoRepository.saveAndFlush(newMemo).getId();
        return id;
    }

    @RequestMapping(value = "/memos/{id}/users/{userId}", method = RequestMethod.PUT)
    public int updateMemo(@PathVariable int id ,@PathVariable int userId , @RequestBody MemoForm memoForm){

        if(memoRepository.existsById(userId)){
            if(memoRepository.existsById(id)){
                var newMemo = memoForm.createMemo();
                newMemo.setId(id);
                newMemo.setUserId(userId);
//                java.sql.Date sqlDate = new Date(System.currentTimeMillis());
//                newMemo.setCreatedAt(sqlDate);
//                memoForm.setId(id);
//                memoForm.setUserId(userId);
                memoRepository.saveAndFlush(newMemo);

                return id;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Memo Id");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User Id");
    }
//
//    @RequestMapping(value = "/memos/{id}", method = RequestMethod.DELETE)
//    public int deleteMemo(@PathVariable int id){
//        memoRepository.deleteById(id);
//        return id;
//    }
}