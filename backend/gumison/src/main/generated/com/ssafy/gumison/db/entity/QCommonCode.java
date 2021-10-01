package com.ssafy.gumison.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonCode is a Querydsl query type for CommonCode
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommonCode extends EntityPathBase<CommonCode> {

    private static final long serialVersionUID = 771293344L;

    public static final QCommonCode commonCode = new QCommonCode("commonCode");

    public final StringPath code = createString("code");

    public final StringPath name = createString("name");

    public final NumberPath<Long> solutionExp = createNumber("solutionExp", Long.class);

    public final NumberPath<Long> tierExp = createNumber("tierExp", Long.class);

    public final StringPath type = createString("type");

    public final EnumPath<com.ssafy.gumison.common.enums.CodeTypeName> typeName = createEnum("typeName", com.ssafy.gumison.common.enums.CodeTypeName.class);

    public QCommonCode(String variable) {
        super(CommonCode.class, forVariable(variable));
    }

    public QCommonCode(Path<? extends CommonCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonCode(PathMetadata metadata) {
        super(CommonCode.class, metadata);
    }

}

