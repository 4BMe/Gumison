package com.ssafy.gumison.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSolutionVideo is a Querydsl query type for SolutionVideo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSolutionVideo extends EntityPathBase<SolutionVideo> {

    private static final long serialVersionUID = -866998438L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSolutionVideo solutionVideo = new QSolutionVideo("solutionVideo");

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSolution solution;

    public final StringPath url = createString("url");

    public QSolutionVideo(String variable) {
        this(SolutionVideo.class, forVariable(variable), INITS);
    }

    public QSolutionVideo(Path<? extends SolutionVideo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSolutionVideo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSolutionVideo(PathMetadata metadata, PathInits inits) {
        this(SolutionVideo.class, metadata, inits);
    }

    public QSolutionVideo(Class<? extends SolutionVideo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.solution = inits.isInitialized("solution") ? new QSolution(forProperty("solution"), inits.get("solution")) : null;
    }

}

