package com.github.stefvanschie.quickskript.core.psi.expression;

import com.github.stefvanschie.quickskript.core.context.Context;
import com.github.stefvanschie.quickskript.core.pattern.SkriptPattern;
import com.github.stefvanschie.quickskript.core.psi.PsiElement;
import com.github.stefvanschie.quickskript.core.psi.PsiElementFactory;
import com.github.stefvanschie.quickskript.core.psi.exception.ExecutionException;
import com.github.stefvanschie.quickskript.core.psi.exception.ParseException;
import com.github.stefvanschie.quickskript.core.psi.util.parsing.pattern.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hashes a string using the specified hash algorithm.
 *
 * @since 0.1.0
 */
public class PsiHashExpression extends PsiElement<String> {

    /**
     * The text to hash
     */
    private PsiElement<?> text;

    /**
     * The message digest to hash with
     */
    private MessageDigest messageDigest;

    /**
     * Creates a new element with the given line number
     *
     * @param text the text to hash, see {@link #text}
     * @param lineNumber the line number this element is associated with
     * @since 0.1.0
     */
    private PsiHashExpression(@NotNull PsiElement<?> text, @NotNull MessageDigest messageDigest, int lineNumber) {
        super(lineNumber);

        this.text = text;
        this.messageDigest = messageDigest;

        if (this.text.isPreComputed()) {
            preComputed = executeImpl(null);

            this.text = null;
            this.messageDigest = null;
        }
    }

    @Nullable
    @Override
    protected String executeImpl(@Nullable Context context) {
        Object object = text.execute(context);

        if (object == null) {
            throw new ExecutionException("Text should not be null", lineNumber);
        }

        byte[] bytes = messageDigest.digest(object.toString().getBytes());
        StringBuilder builder = new StringBuilder(bytes.length * 2);

        for (byte b : bytes) {
            builder
                .append(Character.forDigit((b & 0xF0) >> 4, 16))
                .append(Character.forDigit(b & 0x0F, 16));
        }

        return builder.toString();
    }

    /**
     * A factory for creating {@link PsiHashExpression}s
     *
     * @since 0.1.0
     */
    public static class Factory implements PsiElementFactory {

        /**
         * The pattern to match MD5 {@link PsiHashExpression}s
         */
        @NotNull
        private final SkriptPattern md5Pattern = SkriptPattern.parse("%texts% hash[ed] with MD5");

        /**
         * The pattern to match SHA-256 {@link PsiHashExpression}s
         */
        @NotNull
        private final SkriptPattern sha256Pattern = SkriptPattern.parse("%texts% hash[ed] with SHA-256");

        /**
         * Parses the {@link #md5Pattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to hash using MD5
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("md5Pattern")
        public PsiHashExpression parseMD5(@NotNull PsiElement<?> text, int lineNumber) {
            try {
                return create(text, MessageDigest.getInstance("MD5"), lineNumber);
            } catch (NoSuchAlgorithmException e) {
                throw new ParseException(e, lineNumber);
            }
        }

        /**
         * Parses the {@link #sha256Pattern} and invokes this method with its types if the match succeeds
         *
         * @param text the text to hash using SHA-256
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        @Pattern("sha256Pattern")
        public PsiHashExpression parseSHA256(@NotNull PsiElement<?> text, int lineNumber) {
            try {
                return create(text, MessageDigest.getInstance("SHA-256"), lineNumber);
            } catch (NoSuchAlgorithmException e) {
                throw new ParseException(e, lineNumber);
            }
        }

        /**
         * Provides a default way for creating the specified object for this factory with the given parameters as
         * constructor parameters.
         *
         * @param text the text to hash
         * @param messageDigest the hashing algorithm
         * @param lineNumber the line number
         * @return the expression
         * @since 0.1.0
         */
        @NotNull
        @Contract(pure = true)
        public PsiHashExpression create(@NotNull PsiElement<?> text, @NotNull MessageDigest messageDigest,
            int lineNumber) {
            return new PsiHashExpression(text, messageDigest, lineNumber);
        }
    }
}
