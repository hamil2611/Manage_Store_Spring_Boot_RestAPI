package com.example.managestore.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grid {
    private String gridName;
    private String sort;
    private int limit;
    private int offset;
}
