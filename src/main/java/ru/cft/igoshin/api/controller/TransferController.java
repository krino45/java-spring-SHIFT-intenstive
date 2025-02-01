package ru.cft.igoshin.api.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.cft.igoshin.api.dto.transfer.TransferCreateRequest;
import ru.cft.igoshin.api.dto.transfer.TransferResponse;
import ru.cft.igoshin.api.dto.transfer.enums.TransferType;
import ru.cft.igoshin.core.service.transfer.TransferService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transfers")
@Slf4j
@Validated
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    TransferResponse createTransfer(@RequestBody @Valid TransferCreateRequest request,
                                    @RequestHeader("Authorization") UUID sessionId) {
        log.info("Received a create transfer request. Resulting request: {}", request.toString());
        return transferService.createTransfer(request, sessionId);
    }

    @GetMapping
    List<TransferResponse> getTransfers(@RequestParam(name = "userId", required = false) UUID userId,
                                        @RequestParam(name = "transferType", required = false) TransferType transferType,
                                        @RequestHeader("Authorization") UUID sessionId) {
        log.info("Received a getTransfers request. Parameters: userId:{}, transferType:{}; Auth: {}", userId, transferType, sessionId);
        return transferService.getTransfers(userId, transferType, sessionId);
    }

    @GetMapping("/{transferId}")
    public TransferResponse getTransferById(@PathVariable UUID transferId,
                                            @RequestHeader("Authorization") UUID sessionId) {
        log.info("Received a getTransferById request, transferId: {}", transferId);
        return transferService.getTransferById(transferId, sessionId);
    }
}