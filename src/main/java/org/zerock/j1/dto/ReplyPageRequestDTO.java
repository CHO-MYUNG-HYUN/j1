package org.zerock.j1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyPageRequestDTO extends PageRequestDTO{

  private Long bno;

  @Builder.Default
  private int page = 1;
  
  @Builder.Default
  private int size = 20;

  private boolean last;

}