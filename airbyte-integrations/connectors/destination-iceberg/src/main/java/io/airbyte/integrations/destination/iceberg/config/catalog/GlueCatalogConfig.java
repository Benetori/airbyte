/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.iceberg.config.catalog;

import static io.airbyte.integrations.destination.iceberg.IcebergConstants.CATALOG_NAME;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.iceberg.CatalogProperties;
import org.apache.iceberg.catalog.Catalog;
import org.apache.iceberg.aws.glue.GlueCatalog;
import org.jetbrains.annotations.NotNull;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class GlueCatalogConfig extends IcebergCatalogConfig {

  public GlueCatalogConfig(@NotNull JsonNode catalogConfigJson) {}

  @Override
  public Map<String, String> sparkConfigMap() {
    Map<String, String> configMap = new HashMap<>();
    configMap.put("spark.network.timeout", "300000");
    configMap.put("spark.sql.defaultCatalog", CATALOG_NAME);
    configMap.put("spark.sql.catalog." + CATALOG_NAME, "org.apache.iceberg.spark.SparkCatalog");
    configMap.put("spark.sql.catalog." + CATALOG_NAME + ".catalog-impl", "org.apache.iceberg.aws.glue.GlueCatalog");
    configMap.put("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions");
    configMap.put("spark.driver.extraJavaOptions", "-Dpackaging.type=jar -Djava.io.tmpdir=/tmp");

    configMap.putAll(this.storageConfig.sparkConfigMap(CATALOG_NAME));
    return configMap;
  }

  @Override
  public Catalog genCatalog() {
    GlueCatalog catalog = new GlueCatalog();
    Map<String, String> properties = new HashMap<>(this.storageConfig.catalogInitializeProperties());
    properties.put(CatalogProperties.WAREHOUSE_LOCATION, this.storageConfig.getWarehouseUri());
    catalog.initialize(CATALOG_NAME, properties);
    return catalog;
  }

}
