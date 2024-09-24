package com.sboard.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileEntity is a Querydsl query type for FileEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileEntity extends EntityPathBase<FileEntity> {

    private static final long serialVersionUID = 1714093180L;

    public static final QFileEntity fileEntity = new QFileEntity("fileEntity");

    public final NumberPath<Integer> ano = createNumber("ano", Integer.class);

    public final NumberPath<Integer> download = createNumber("download", Integer.class);

    public final NumberPath<Integer> fno = createNumber("fno", Integer.class);

    public final StringPath oName = createString("oName");

    public final DateTimePath<java.time.LocalDateTime> rdate = createDateTime("rdate", java.time.LocalDateTime.class);

    public final StringPath SName = createString("SName");

    public QFileEntity(String variable) {
        super(FileEntity.class, forVariable(variable));
    }

    public QFileEntity(Path<? extends FileEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileEntity(PathMetadata metadata) {
        super(FileEntity.class, metadata);
    }

}

