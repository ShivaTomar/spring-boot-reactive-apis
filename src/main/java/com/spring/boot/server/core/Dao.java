package com.spring.boot.server.core;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface Dao<T> {
    Maybe<T> get(Integer id);
    Flowable<T> getAll();
    Single<Boolean> save(T t);
    Single<Boolean> update(T t);
    Single<Boolean> delete(T t);
}
