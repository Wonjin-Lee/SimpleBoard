package com.spacewhale.board.service;

import com.spacewhale.board.common.FileUtils;
import com.spacewhale.board.dto.BoardDto;
import com.spacewhale.board.dto.BoardFileDto;
import com.spacewhale.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardMapper.insertBoard(boardDto);
        List<BoardFileDto> list = fileUtils.parseFileInfo(boardDto.getBoardIdx(), multipartHttpServletRequest);

        if (CollectionUtils.isEmpty(list) == false) {
            boardMapper.insertBoardFileList(list);
        }
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception {
        boardMapper.updateHitCount(boardIdx);
        BoardDto board = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
        board.setFileList(fileList);

        boardMapper.updateHitCount(boardIdx);

        return board;
    }

    @Override
    public void updateBoard(BoardDto boardDto) throws Exception {
        boardMapper.updateBoard(boardDto);
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        boardMapper.deleteBoard(boardIdx);
    }

    @Override
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }
}
