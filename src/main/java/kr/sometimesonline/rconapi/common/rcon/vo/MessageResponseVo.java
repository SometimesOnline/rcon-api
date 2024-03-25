package kr.sometimesonline.rconapi.common.rcon.vo;

public record MessageResponseVo<T>(boolean isSuccess ,T body) {
}
