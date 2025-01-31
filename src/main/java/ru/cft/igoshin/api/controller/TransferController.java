package ru.cft.igoshin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.cft.igoshin.api.dto.transfer.TransferCreateRequest;
import ru.cft.igoshin.api.dto.transfer.TransferCreateResponse;
import ru.cft.igoshin.core.service.TransferService;

@RestController
@RequestMapping("/transfers")
@Slf4j
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    TransferCreateResponse createTransfer(@RequestBody TransferCreateRequest request) {
        log.info("Received a create transfer request. Resulting request: {}", request.toString());
        return null;
    }
}