/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */

package io.airbyte.integrations.destination.iceberg.glue;

import io.airbyte.integrations.destination.iceberg.config.format.DataFileFormat;

public class IcebergGlueCatalogS3ParquetIntegrationTest extends BaseIcebergGlueCatalogS3IntegrationTest {

  @Override
  DataFileFormat fileFormat() {
    return DataFileFormat.PARQUET;
  }

}
