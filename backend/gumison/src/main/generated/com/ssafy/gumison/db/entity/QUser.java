package com.ssafy.gumison.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1725150221L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Long> accumulateExp = createNumber("accumulateExp", Long.class);

    public final NumberPath<Integer> accumulateVideo = createNumber("accumulateVideo", Integer.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickname = createString("nickname");

    public final StringPath oauthId = createString("oauthId");

    public final StringPath oauthType = createString("oauthType");

    public final StringPath profile = createString("profile");

    public final ListPath<Solution, QSolution> solutionList = this.<Solution, QSolution>createList("solutionList", Solution.class, QSolution.class, PathInits.DIRECT2);

    public final NumberPath<Integer> tierCode = createNumber("tierCode", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

