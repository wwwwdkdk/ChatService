package com.aiit.netty.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageProtocol {
    private int length;
    private byte[] content;
}
