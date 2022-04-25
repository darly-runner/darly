package com.darly.api.response.record;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.record.RecordMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecordListGetRes extends BaseResponseBody {
    private List<RecordMapping> records;

    @Builder
    public RecordListGetRes(Integer statusCode, String message, List<RecordMapping> records) {
        super(statusCode, message);
        this.records = records;
    }
}
