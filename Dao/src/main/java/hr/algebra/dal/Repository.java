/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Kizo
 * @param <T>
 */
public interface Repository<T> {

    int create(T t) throws Exception;

    void createList(List<T> t) throws Exception;

    void update(int id, T t) throws Exception;

    void delete(int id) throws Exception;

    Optional<T> select(int id) throws Exception;

    List<T> selectAll() throws Exception;

}
