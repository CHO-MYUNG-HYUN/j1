package org.zerock.j1.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.j1.dto.BoradListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

@Transactional
public interface BoardService {
  
  PageResponseDTO<BoradListRcntDTO> listRcnt (PageRequestDTO pageRequestDTO);

}
