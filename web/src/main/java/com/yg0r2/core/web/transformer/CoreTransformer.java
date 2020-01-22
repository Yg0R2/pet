package com.yg0r2.core.web.transformer;

import com.yg0r2.core.api.model.CoreEntry;
import com.yg0r2.core.dao.model.CoreEntity;

public interface CoreTransformer<API extends CoreEntry, DAO extends CoreEntity> {

    API transform(DAO entity);

    DAO transform(API entry);

}
