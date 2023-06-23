package com.aiit.pojo.resp;

import com.aiit.pojo.Dynamic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicResp {
    private boolean hasMore;
    private List<Dynamic> dynamic;
}
