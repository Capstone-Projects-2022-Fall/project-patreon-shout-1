package com.patreonshout.rest;

import com.patreonshout.beans.ListBean;
import com.patreonshout.beans.PostBean;
import com.patreonshout.beans.WebAccount;
import com.patreonshout.beans.request.PostGetMultipleRequest;
import com.patreonshout.jpa.PostsRepository;
import com.patreonshout.jpa.WebAccountFunctions;
import com.patreonshout.rest.interfaces.PostImpl;
import com.patreonshout.utils.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Post RESTful Endpoint Interface
 */
@RestController
public class PostSvc extends BaseSvc implements PostImpl {

    /**
     * An autowired Spring component that endpoints utilize to send or receive data from the posts table in the database
     */
    @Autowired
    PostsRepository postsRepository;

    /**
     * An autowired Spring component that endpoints utilize to send or receive data from the database
     */
    @Autowired
    private WebAccountFunctions webAccountFunctions;

    /**
     * {@inheritDoc}
     */
    public ResponseEntity<?> GetCreatorPosts(@RequestParam(name = "creator") String creator) { // TODO: SOON TO BE DEPRECATED
        List<PostBean> posts = postsRepository.findAllByCreatorPageUrl(creator);

        List<Map<String, String>> response = new ArrayList<>();

        for (PostBean pb : posts) {
            if (!pb.isPublic()) {
                pb.setContent("This post is private");
            }

            Map<String, String> listResponse = new HashMap<>();
            listResponse.put("title", pb.getTitle());
            listResponse.put("creator_page_url", pb.getCreatorPageUrl());
            listResponse.put("url", pb.getUrl());
            listResponse.put("content", pb.getContent());
            listResponse.put("published_at", pb.getPublishDate());
            listResponse.put("is_public", String.valueOf(pb.isPublic()));

            response.add(listResponse);
        }

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    /**
     * {@inheritDoc}
     */
    public ResponseEntity<?> GetMultipleCreatorPosts(@RequestBody PostGetMultipleRequest postGetMultipleRequest) {
        WebAccount userAccount = webAccountFunctions.findByLoginToken(postGetMultipleRequest.getLoginToken());

        if (userAccount == null) {
            return ResponseUtil.Generic(HttpStatus.BAD_REQUEST, "Invalid login token.");
        }

        Page<PostBean> page = postsRepository.getMultipleCreatorPosts(postGetMultipleRequest.getCreators(), PageRequest.of(postGetMultipleRequest.getPage(), 5).withSort(Sort.Direction.ASC, "publishdate"));

        for (PostBean pb : page.getContent()) {
            if (!pb.isPublic()) {
                pb.setContent("This post is private");
            }
        }

        // TODO: might need to construct response since PostBean is related to other beans and jackson cannot correctly get the data from the other beans relating to PostBean (look at the endpoint above)
        return new ResponseEntity<>(page, HttpStatus.FOUND);
    }
}
