package nl.fhict.happynews.shared;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 169967080L;

    public static final QPost post = new QPost("post");

    public final StringPath author = createString("author");

    public final StringPath contentText = createString("contentText");

    public final DateTimePath<org.joda.time.DateTime> expirationDate = createDateTime("expirationDate", org.joda.time.DateTime.class);

    public final BooleanPath hidden = createBoolean("hidden");

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final DateTimePath<org.joda.time.DateTime> indexedAt = createDateTime("indexedAt", org.joda.time.DateTime.class);

    public final NumberPath<Double> positivityScore = createNumber("positivityScore", Double.class);

    public final DateTimePath<org.joda.time.DateTime> publishedAt = createDateTime("publishedAt", org.joda.time.DateTime.class);

    public final StringPath source = createString("source");

    public final StringPath sourceName = createString("sourceName");

    public final ListPath<String, StringPath> tags = this.<String, StringPath>createList("tags", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final EnumPath<Post.Type> type = createEnum("type", Post.Type.class);

    public final StringPath url = createString("url");

    public final StringPath uuid = createString("uuid");

    public final StringPath videoUrl = createString("videoUrl");

    public QPost(String variable) {
        super(Post.class, forVariable(variable));
    }

    public QPost(Path<? extends Post> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPost(PathMetadata metadata) {
        super(Post.class, metadata);
    }

}

