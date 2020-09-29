/*
 * Copyright 2020 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.smithy.codegen.core.writer;

import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.utils.CodeWriter;
import software.amazon.smithy.utils.SmithyUnstableApi;

/**
 * Contains the imports associated with a specific file.
 *
 * <p>The only required method is {@link #importSymbol}, but implementations
 * are expected to also override {@link #toString()} so that it contains the
 * formatted imports that can be written as code to a file. Other methods
 * can, and should, be added to make working with language specific imports
 * easier too.
 */
@SmithyUnstableApi
public interface ImportContainer {
    /**
     * Adds an import for the given symbol if and only if the "namespace" of the
     * provided Symbol differs from the "namespace" associated with the
     * ImportContainer.
     *
     * <p>"namespace" in this context can mean whatever it needs to mean for the
     * target programming language. In some languages, it might mean the path to
     * a file. In others, it might mean a proper namespace string. It's up to
     * subclasses to both track a current "namespace" and implement this method
     * in a way that makes sense.
     *
     * @param symbol Symbol to import if it's in another namespace.
     * @param alias  Alias to import the symbol as.
     */
    void importSymbol(Symbol symbol, String alias);

    /**
     * Implementations must implement a custom {@code toString} method that
     * converts the collected imports to code that can be written to a
     * {@link CodeWriter}.
     *
     * @return Returns the collected imports as a string.
     */
    String toString();
}
