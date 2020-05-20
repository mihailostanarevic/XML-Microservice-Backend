package com.rentacar.messageservice.controller;

import com.rentacar.messageservice.dto.request.SendMessageRequest;
import com.rentacar.messageservice.dto.response.MessageResponse;
import com.rentacar.messageservice.service.IMessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final IMessageService _messageService;

    public MessageController(IMessageService messageService) {
        _messageService = messageService;
    }

    @PostMapping
    public MessageResponse sendMessage(@RequestBody SendMessageRequest request) throws Exception{
        return _messageService.sendMessage(request);
    }

    @DeleteMapping("/{id}/message")
    public void deleteMessage(@PathVariable UUID id) throws Exception{
        _messageService.deleteMessage(id);
    }

    @GetMapping("/{id}/message")
    public MessageResponse getMessage(@PathVariable UUID id) throws Exception{
        return _messageService.getMessage(id);
    }

    @GetMapping
    public List<MessageResponse> getAllMessages() throws Exception{
        return _messageService.getAllMessages();
    }

    @GetMapping("/{id}/user")
    public List<MessageResponse> getAllMessagesByUser(@PathVariable UUID id) throws Exception{
        return _messageService.getAllMessagesByUser(id);
    }

    @GetMapping("/{id}/sender")
    public List<MessageResponse> getAllMessagesBySender(@PathVariable UUID id) throws Exception{
        return _messageService.getAllMessagesBySender(id);
    }

    @GetMapping("/{id}/recipient")
    public List<MessageResponse> getAllMessagesByRecipient(@PathVariable UUID id) throws Exception{
        return _messageService.getAllMessagesByRecipient(id);
    }
}
