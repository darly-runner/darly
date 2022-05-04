package com.darly.api.controller;

import com.darly.api.response.address.AddressesGetRes;
import com.darly.api.service.address.AddressService;
import com.darly.db.entity.address.Address;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="address Api", tags={"Address"})
@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    AddressService addressService;

    // 1. 지역목록 GET
    @GetMapping
    @ApiOperation(value="지역목록", notes="지역목록 가져오기")
    @ApiResponses({
            @ApiResponse(code=200, message="테스트 성공"),
            @ApiResponse(code=404, message="잘못된 url 접근"),
            @ApiResponse(code=500, message="서버 에러")
    })
    public ResponseEntity<AddressesGetRes> getAddresses(String address) {
        List<Address> addresses = addressService.getAddresses(address);

        return ResponseEntity.ok(AddressesGetRes.of(addresses, 200 ,"success"));
    }
}
