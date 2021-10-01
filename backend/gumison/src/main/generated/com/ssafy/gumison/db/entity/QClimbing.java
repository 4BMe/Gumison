package com.ssafy.gumison.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QClimbing is a Querydsl query type for Climbing
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClimbing extends EntityPathBase<Climbing> {

    private static final long serialVersionUID = -1744214731L;

    public static final QClimbing climbing = new QClimbing("climbing");

    public final StringPath address = createString("address");

    public final StringPath climbingName = createString("climbingName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public QClimbing(String variable) {
        super(Climbing.class, forVariable(variable));
    }

    public QClimbing(Path<? extends Climbing> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClimbing(PathMetadata metadata) {
        super(Climbing.class, metadata);
    }

}

