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

import java.util.Map;
import java.util.TreeMap;
import software.amazon.smithy.codegen.core.Symbol;
import software.amazon.smithy.codegen.core.SymbolProvider;
import software.amazon.smithy.model.shapes.Shape;

/**
 * A pretty basic implementation of CodegenWriter.
 */
final class MyWriter extends CodegenWriter<MyWriter, MyWriter.MyImportContainer> {

    public MyWriter(String namespace) {
        super(new TestDocumentationWriter(), new MyImportContainer(namespace));
    }

    static final class MyImportContainer implements ImportContainer {
        public final Map<String, String> imports = new TreeMap<>();
        private final String namespace;

        private MyImportContainer(String namespace) {
            this.namespace = namespace;
        }

        @Override
        public void importSymbol(Symbol symbol, String alias) {
            if (!symbol.getNamespace().equals(namespace)) {
                imports.put(alias, symbol.toString());
            }
        }
    }

    static final class TestDocumentationWriter implements DocumentationWriter<MyWriter> {
        @Override
        public void writeDocs(MyWriter writer, Runnable runnable) {
            writer.pushFilteredState(this::sanitizeDocString);
            writer.write("Before");
            runnable.run();
            writer.write("After");
            writer.popState();
        }

        private String sanitizeDocString(String docs) {
            return docs.replace("!", "!!");
        }
    }

    static final class MyObserver implements UseShapeWriterObserver<MyWriter> {
        @Override
        public void observe(Shape shape, Symbol symbol, SymbolProvider symbolProvider, MyWriter writer) {
            writer.write("/// Writing $L", shape.getId());
        }
    }
}
