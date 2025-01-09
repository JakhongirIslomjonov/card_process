package uz.dev.cardprocess.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class    DataDTO<T> implements Serializable {

    private T data;

    private AppErrorDTO error;

    private boolean success;

    public DataDTO(boolean success) {
        this.success = success;
    }

    public DataDTO(T data) {
        this.data = data;
        this.success = true;
    }

    public DataDTO(AppErrorDTO error) {
        this.error = error;
        this.success = false;
    }

    public static <T> DataDTO<T> of(T data) {
        return new DataDTO<>(data);
    }

}
