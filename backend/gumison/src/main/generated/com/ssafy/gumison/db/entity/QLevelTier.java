package com.ssafy.gumison.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLevelTier is a Querydsl query type for LevelTier
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLevelTier extends EntityPathBase<LevelTier> {

    private static final long serialVersionUID = 1794144350L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLevelTier levelTier = new QLevelTier("levelTier");

    public final QClimbing climbing;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> levelCode = createNumber("levelCode", Integer.class);

    public final NumberPath<Integer> tierCode = createNumber("tierCode", Integer.class);

    public QLevelTier(String variable) {
        this(LevelTier.class, forVariable(variable), INITS);
    }

    public QLevelTier(Path<? extends LevelTier> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLevelTier(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLevelTier(PathMetadata metadata, PathInits inits) {
        this(LevelTier.class, metadata, inits);
    }

    public QLevelTier(Class<? extends LevelTier> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.climbing = inits.isInitialized("climbing") ? new QClimbing(forProperty("climbing")) : null;
    }

}

