package com.example.first.service;

import com.example.first.dao.DeptDao;
import com.example.first.dto.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//서비스 역할을 하는 클래스는 반드시 @serivce annotation
//상속받고 있는 자식 클래스에 써야함
@Service
public class DeptServiceImpl implements DeptService{
    @Autowired // < 자동으로 인터페이스 객체를 생성해준다 @AutoWired
    DeptDao deptDao; // DeptDao deptDao = new DeptDaoImpl(); -> 의존성 주입 DI (Dependancy Injection)
    @Override
    public List<Dept> selectList() {
        //service 부분은 원래 비즈니스 로직 작성
        return deptDao.selectList(); //List<Dept> 객체 리턴 DB에서 가져온 데이터
    }
}
