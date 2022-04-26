package com.darly.api.response.record;

import com.darly.common.model.response.BaseResponseBody;
import com.darly.db.entity.record.RecordMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("RecordListGetResponse")
public class RecordListGetRes extends BaseResponseBody {
    @ApiModelProperty(name="records", example="[record1, record2, record3]")
    private List<RecordMapping> records;

    @Builder
    public RecordListGetRes(Integer statusCode, String message, List<RecordMapping> records) {
        super(statusCode, message);
        this.records = records;
    }
}
