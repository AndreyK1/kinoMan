package ru.chuhan.demo.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

/**
 * There're a couple of things to note when using this filter. <a href="https://stackoverflow.com/a/51279460/1961634">Visit stackoverflow for an example</a>
 * <ul>
 * <li>arrays provide an additional depth level, so array entry is depth+2
 * from parent; it's not a bug, it's a feature - this behavior could be
 * changed if JsonStreamContext is parsed for array start</li>
 * <li>map properties are serialized fully at the level the map is declared</li>
 * <li>depth is defined per-class; you could extend base class with DN suffix
 * to serialize if you need variable length, or make a constant filter name
 * and create a dedicated `ObjectMapper` for each depth</li>
 * </ul>
 * @author Dariusz Wawer <dwawer@pretius.com>
 *
 */
public class DeepFieldFilter extends SimpleBeanPropertyFilter {
    private final int maxDepth;

    public DeepFieldFilter(int maxDepth) {
        super();
        this.maxDepth = maxDepth;
    }

    private int calcDepth(PropertyWriter writer, JsonGenerator jgen) {
        JsonStreamContext sc = jgen.getOutputContext();
        int depth = -1;
        while (sc != null) {
            sc = sc.getParent();
            depth++;
        }
        return depth;
    }

    @Override
    public void serializeAsField(Object pojo, JsonGenerator gen, SerializerProvider provider, PropertyWriter writer)
            throws Exception {
        int depth = calcDepth(writer, gen);
        if (depth <= maxDepth) {
            writer.serializeAsField(pojo, gen, provider);
        }
        // comment this if you don't want {} placeholders
        else {
            writer.serializeAsOmittedField(pojo, gen, provider);
        }
    }

}
