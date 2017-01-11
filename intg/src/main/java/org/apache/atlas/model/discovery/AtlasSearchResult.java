/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.atlas.model.discovery;

import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.codehaus.jackson.annotate.JsonAutoDetect.Visibility.NONE;
import static org.codehaus.jackson.annotate.JsonAutoDetect.Visibility.PUBLIC_ONLY;

@JsonAutoDetect(getterVisibility = PUBLIC_ONLY, setterVisibility = PUBLIC_ONLY, fieldVisibility = NONE)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AtlasSearchResult implements Serializable {
    private String                    queryText;
    private AtlasQueryType            queryType;
    private List<AtlasEntityHeader>   entities;
    private AttributeSearchResult     attributes;
    private List<AtlasFullTextResult> fullTextResult;

    public AtlasSearchResult() {}

    public AtlasSearchResult(String queryText, AtlasQueryType queryType) {
        setQueryText(queryText);
        setQueryType(queryType);
        setEntities(null);
        setAttributes(null);
        setFullTextResult(null);
    }

    public String getQueryText() { return queryText; }

    public void setQueryText(String queryText) { this.queryText = queryText; }

    public AtlasQueryType getQueryType() { return queryType; }

    public void setQueryType(AtlasQueryType queryType) { this.queryType = queryType; }

    public List<AtlasEntityHeader> getEntities() { return entities; }

    public void setEntities(List<AtlasEntityHeader> entities) { this.entities = entities; }

    public AttributeSearchResult getAttributes() { return attributes; }

    public void setAttributes(AttributeSearchResult attributes) { this.attributes = attributes; }

    public List<AtlasFullTextResult> getFullTextResult() { return fullTextResult; }

    public void setFullTextResult(List<AtlasFullTextResult> fullTextResult) { this.fullTextResult = fullTextResult; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtlasSearchResult that = (AtlasSearchResult) o;
        return Objects.equals(queryText, that.queryText) &&
               Objects.equals(queryType, that.queryType) &&
               Objects.equals(entities, that.entities) &&
               Objects.equals(attributes, that.attributes) &&
               Objects.equals(fullTextResult, that.fullTextResult);
    }

    @Override
    public int hashCode() { return Objects.hash(queryText, queryType, entities, attributes, fullTextResult); }

    @Override
    public String toString() {
        return "AtlasSearchResult{" +
                "queryText='" + queryText + '\'' +
                ", queryType=" + queryType +
                ", entities=" + entities +
                ", attributes=" + attributes +
                ", fullTextResult=" + fullTextResult +
                '}';
    }

    public void addEntity(AtlasEntityHeader newEntity) {
        if (entities == null) {
            entities = new ArrayList<>();
        }

        if (entities.isEmpty()) {
            entities.add(newEntity);
        } else {
            removeEntity(newEntity);
            entities.add(newEntity);
        }
    }

    public void removeEntity(AtlasEntityHeader entity) {
        List<AtlasEntityHeader> entities = this.entities;

        if (CollectionUtils.isNotEmpty(entities)) {
            Iterator<AtlasEntityHeader> iter = entities.iterator();
            while (iter.hasNext()) {
                AtlasEntityHeader currEntity = iter.next();
                if (StringUtils.equals(currEntity.getGuid(), entity.getGuid())) {
                    iter.remove();
                }
            }
        }
    }

    public enum AtlasQueryType { DSL, FULL_TEXT, GREMLIN }

    @JsonAutoDetect(getterVisibility = PUBLIC_ONLY, setterVisibility = PUBLIC_ONLY, fieldVisibility = NONE)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class AttributeSearchResult {
        private List<String>       name;
        private List<List<Object>> values;

        public AttributeSearchResult() { }

        public AttributeSearchResult(List<String> name, List<List<Object>> values) {
            this.name = name;
            this.values = values;
        }

        public List<String> getName() { return name; }

        public void setName(List<String> name) { this.name = name; }

        public List<List<Object>> getValues() { return values; }

        public void setValues(List<List<Object>> values) { this.values = values; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AttributeSearchResult that = (AttributeSearchResult) o;
            return Objects.equals(name, that.name) &&
                    Objects.equals(values, that.values);
        }

        @Override
        public int hashCode() { return Objects.hash(name, values); }

        @Override
        public String toString() {
            return "AttributeSearchResult{" +
                    "name="   + name + ", " +
                    "values=" + values +
                    '}';
        }
    }

    @JsonAutoDetect(getterVisibility = PUBLIC_ONLY, setterVisibility = PUBLIC_ONLY, fieldVisibility = NONE)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.PROPERTY)
    public static class AtlasFullTextResult {
        AtlasEntityHeader entity;
        Double            score;

        public AtlasFullTextResult() {}

        public AtlasFullTextResult(AtlasEntityHeader entity, Double score) {
            this.entity = entity;
            this.score  = score;
        }

        public AtlasEntityHeader getEntity() { return entity; }

        public void setEntity(AtlasEntityHeader entity) { this.entity = entity; }

        public Double getScore() { return score; }

        public void setScore(Double score) { this.score = score; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AtlasFullTextResult that = (AtlasFullTextResult) o;
            return Objects.equals(entity, that.entity) &&
                   Objects.equals(score, that.score);
        }

        @Override
        public int hashCode() { return Objects.hash(entity, score); }

        @Override
        public String toString() {
            return "AtlasFullTextResult{" +
                    "entity=" + entity +
                    ", score=" + score +
                    '}';
        }
    }
}