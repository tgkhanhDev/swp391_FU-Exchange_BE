package com.adkp.fuexchange.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaPostProduct {

    private long total;

    private int current;

}
