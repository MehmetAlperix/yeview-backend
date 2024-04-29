package com.kolaykira.webapp.comment;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/comment")
@AllArgsConstructor
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addcomment")
    public String addContract(@RequestBody CommentRequest request) throws ExecutionException, InterruptedException
    {
        return commentService.addComment( CommentRequestToComment.requestToContract(request) );
    }

    @DeleteMapping(path = "{comment_id}")
    public String deleteContract(@PathVariable("comment_id") String contractId) throws ExecutionException, InterruptedException {
        return commentService.deleteComment(contractId);
    }
    @GetMapping
    public ResponseEntity<List<Comment>> getComments() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(commentService.getComments());

    }
    @GetMapping(path = "{commentId}")
    public Comment getContractById(@PathVariable(name = "commentId") String contractId) throws ExecutionException, InterruptedException {
        return commentService.getCommentsById(contractId);
    }
    @GetMapping(path = "userdatacomment/{useremail}")
    public List<Comment> getCommentsByUser(@PathVariable(name = "useremail") String userEmail) throws ExecutionException, InterruptedException {
        return commentService.getCommentsByUser(userEmail);
    }
    @GetMapping(path = "usercomments/{useremail}")
    public List<CommentShowcase> getCommentShowcasesByUser(@PathVariable(name = "useremail") String userEmail) throws ExecutionException, InterruptedException {
        return commentService.getCommentShowcaseByUser(userEmail);
    }


    @GetMapping(path = "menucomments/{menuid}")
    public List<CommentShowcase> getCommentShowcasesByMenu(@PathVariable(name = "menuid") String menuId) throws ExecutionException, InterruptedException {
        return commentService.getCommentShowcaseByCommentID(menuId);
    }
}
