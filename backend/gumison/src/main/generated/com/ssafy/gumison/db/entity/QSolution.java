package com.ssafy.gumison.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSolution is a Querydsl query type for Solution
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSolution extends EntityPathBase<Solution> {

    private static final long serialVersionUID = -1167697503L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSolution solution = new QSolution("solution");

    public final NumberPath<Integer> accumulateReport = createNumber("accumulateReport", Integer.class);

    public final QClimbing climbing;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final BooleanPath deleteYN = createBoolean("deleteYN");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLevelTier levelTier;

    public final ListPath<SolutionVideo, QSolutionVideo> solutionVideoList = this.<SolutionVideo, QSolutionVideo>createList("solutionVideoList", SolutionVideo.class, QSolutionVideo.class, PathInits.DIRECT2);

    public final QUser user;

    public QSolution(String variable) {
        this(Solution.class, forVariable(variable), INITS);
    }

    public QSolution(Path<? extends Solution> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSolution(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSolution(PathMetadata metadata, PathInits inits) {
        this(Solution.class, metadata, inits);
    }

    public QSolution(Class<? extends Solution> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.climbing = inits.isInitialized("climbing") ? new QClimbing(forProperty("climbing")) : null;
        this.levelTier = inits.isInitialized("levelTier") ? new QLevelTier(forProperty("levelTier"), inits.get("levelTier")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

