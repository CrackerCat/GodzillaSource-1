package net.BeichenDream.Godzilla.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CryptionAnnotation {
    String Name();

    String payloadName();
}
