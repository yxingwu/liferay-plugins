/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.samplealloymvc.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.samplealloymvc.exception.NoSuchDefinitionException;
import com.liferay.samplealloymvc.model.Definition;
import com.liferay.samplealloymvc.model.impl.DefinitionImpl;
import com.liferay.samplealloymvc.model.impl.DefinitionModelImpl;
import com.liferay.samplealloymvc.service.persistence.DefinitionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DefinitionPersistence
 * @see com.liferay.samplealloymvc.service.persistence.DefinitionUtil
 * @generated
 */
@ProviderType
public class DefinitionPersistenceImpl extends BasePersistenceImpl<Definition>
	implements DefinitionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DefinitionUtil} to access the definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DefinitionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
			DefinitionModelImpl.FINDER_CACHE_ENABLED, DefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
			DefinitionModelImpl.FINDER_CACHE_ENABLED, DefinitionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
			DefinitionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public DefinitionPersistenceImpl() {
		setModelClass(Definition.class);
	}

	/**
	 * Caches the definition in the entity cache if it is enabled.
	 *
	 * @param definition the definition
	 */
	@Override
	public void cacheResult(Definition definition) {
		entityCache.putResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
			DefinitionImpl.class, definition.getPrimaryKey(), definition);

		definition.resetOriginalValues();
	}

	/**
	 * Caches the definitions in the entity cache if it is enabled.
	 *
	 * @param definitions the definitions
	 */
	@Override
	public void cacheResult(List<Definition> definitions) {
		for (Definition definition : definitions) {
			if (entityCache.getResult(
						DefinitionModelImpl.ENTITY_CACHE_ENABLED,
						DefinitionImpl.class, definition.getPrimaryKey()) == null) {
				cacheResult(definition);
			}
			else {
				definition.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all definitions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DefinitionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the definition.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Definition definition) {
		entityCache.removeResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
			DefinitionImpl.class, definition.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Definition> definitions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Definition definition : definitions) {
			entityCache.removeResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
				DefinitionImpl.class, definition.getPrimaryKey());
		}
	}

	/**
	 * Creates a new definition with the primary key. Does not add the definition to the database.
	 *
	 * @param definitionId the primary key for the new definition
	 * @return the new definition
	 */
	@Override
	public Definition create(long definitionId) {
		Definition definition = new DefinitionImpl();

		definition.setNew(true);
		definition.setPrimaryKey(definitionId);

		definition.setCompanyId(companyProvider.getCompanyId());

		return definition;
	}

	/**
	 * Removes the definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param definitionId the primary key of the definition
	 * @return the definition that was removed
	 * @throws NoSuchDefinitionException if a definition with the primary key could not be found
	 */
	@Override
	public Definition remove(long definitionId)
		throws NoSuchDefinitionException {
		return remove((Serializable)definitionId);
	}

	/**
	 * Removes the definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the definition
	 * @return the definition that was removed
	 * @throws NoSuchDefinitionException if a definition with the primary key could not be found
	 */
	@Override
	public Definition remove(Serializable primaryKey)
		throws NoSuchDefinitionException {
		Session session = null;

		try {
			session = openSession();

			Definition definition = (Definition)session.get(DefinitionImpl.class,
					primaryKey);

			if (definition == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefinitionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(definition);
		}
		catch (NoSuchDefinitionException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Definition removeImpl(Definition definition) {
		definition = toUnwrappedModel(definition);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(definition)) {
				definition = (Definition)session.get(DefinitionImpl.class,
						definition.getPrimaryKeyObj());
			}

			if (definition != null) {
				session.delete(definition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (definition != null) {
			clearCache(definition);
		}

		return definition;
	}

	@Override
	public Definition updateImpl(Definition definition) {
		definition = toUnwrappedModel(definition);

		boolean isNew = definition.isNew();

		DefinitionModelImpl definitionModelImpl = (DefinitionModelImpl)definition;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (definition.getCreateDate() == null)) {
			if (serviceContext == null) {
				definition.setCreateDate(now);
			}
			else {
				definition.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!definitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				definition.setModifiedDate(now);
			}
			else {
				definition.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (definition.isNew()) {
				session.save(definition);

				definition.setNew(false);
			}
			else {
				definition = (Definition)session.merge(definition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		entityCache.putResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
			DefinitionImpl.class, definition.getPrimaryKey(), definition, false);

		definition.resetOriginalValues();

		return definition;
	}

	protected Definition toUnwrappedModel(Definition definition) {
		if (definition instanceof DefinitionImpl) {
			return definition;
		}

		DefinitionImpl definitionImpl = new DefinitionImpl();

		definitionImpl.setNew(definition.isNew());
		definitionImpl.setPrimaryKey(definition.getPrimaryKey());

		definitionImpl.setDefinitionId(definition.getDefinitionId());
		definitionImpl.setGroupId(definition.getGroupId());
		definitionImpl.setCompanyId(definition.getCompanyId());
		definitionImpl.setUserId(definition.getUserId());
		definitionImpl.setUserName(definition.getUserName());
		definitionImpl.setCreateDate(definition.getCreateDate());
		definitionImpl.setModifiedDate(definition.getModifiedDate());
		definitionImpl.setTypeId(definition.getTypeId());
		definitionImpl.setManufacturer(definition.getManufacturer());
		definitionImpl.setModel(definition.getModel());
		definitionImpl.setOrderDate(definition.getOrderDate());
		definitionImpl.setQuantity(definition.getQuantity());
		definitionImpl.setPrice(definition.getPrice());

		return definitionImpl;
	}

	/**
	 * Returns the definition with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the definition
	 * @return the definition
	 * @throws NoSuchDefinitionException if a definition with the primary key could not be found
	 */
	@Override
	public Definition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefinitionException {
		Definition definition = fetchByPrimaryKey(primaryKey);

		if (definition == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefinitionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return definition;
	}

	/**
	 * Returns the definition with the primary key or throws a {@link NoSuchDefinitionException} if it could not be found.
	 *
	 * @param definitionId the primary key of the definition
	 * @return the definition
	 * @throws NoSuchDefinitionException if a definition with the primary key could not be found
	 */
	@Override
	public Definition findByPrimaryKey(long definitionId)
		throws NoSuchDefinitionException {
		return findByPrimaryKey((Serializable)definitionId);
	}

	/**
	 * Returns the definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the definition
	 * @return the definition, or <code>null</code> if a definition with the primary key could not be found
	 */
	@Override
	public Definition fetchByPrimaryKey(Serializable primaryKey) {
		Definition definition = (Definition)entityCache.getResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
				DefinitionImpl.class, primaryKey);

		if (definition == _nullDefinition) {
			return null;
		}

		if (definition == null) {
			Session session = null;

			try {
				session = openSession();

				definition = (Definition)session.get(DefinitionImpl.class,
						primaryKey);

				if (definition != null) {
					cacheResult(definition);
				}
				else {
					entityCache.putResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
						DefinitionImpl.class, primaryKey, _nullDefinition);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
					DefinitionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return definition;
	}

	/**
	 * Returns the definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param definitionId the primary key of the definition
	 * @return the definition, or <code>null</code> if a definition with the primary key could not be found
	 */
	@Override
	public Definition fetchByPrimaryKey(long definitionId) {
		return fetchByPrimaryKey((Serializable)definitionId);
	}

	@Override
	public Map<Serializable, Definition> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, Definition> map = new HashMap<Serializable, Definition>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			Definition definition = fetchByPrimaryKey(primaryKey);

			if (definition != null) {
				map.put(primaryKey, definition);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Definition definition = (Definition)entityCache.getResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
					DefinitionImpl.class, primaryKey);

			if (definition == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, definition);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DEFINITION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (Definition definition : (List<Definition>)q.list()) {
				map.put(definition.getPrimaryKeyObj(), definition);

				cacheResult(definition);

				uncachedPrimaryKeys.remove(definition.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DefinitionModelImpl.ENTITY_CACHE_ENABLED,
					DefinitionImpl.class, primaryKey, _nullDefinition);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the definitions.
	 *
	 * @return the definitions
	 */
	@Override
	public List<Definition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of definitions
	 * @param end the upper bound of the range of definitions (not inclusive)
	 * @return the range of definitions
	 */
	@Override
	public List<Definition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of definitions
	 * @param end the upper bound of the range of definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of definitions
	 */
	@Override
	public List<Definition> findAll(int start, int end,
		OrderByComparator<Definition> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of definitions
	 * @param end the upper bound of the range of definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of definitions
	 */
	@Override
	public List<Definition> findAll(int start, int end,
		OrderByComparator<Definition> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Definition> list = null;

		if (retrieveFromCache) {
			list = (List<Definition>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DEFINITION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DEFINITION;

				if (pagination) {
					sql = sql.concat(DefinitionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Definition>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<Definition>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (Definition definition : findAll()) {
			remove(definition);
		}
	}

	/**
	 * Returns the number of definitions.
	 *
	 * @return the number of definitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DEFINITION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the definition persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DefinitionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DEFINITION = "SELECT definition FROM Definition definition";
	private static final String _SQL_SELECT_DEFINITION_WHERE_PKS_IN = "SELECT definition FROM Definition definition WHERE definitionId IN (";
	private static final String _SQL_COUNT_DEFINITION = "SELECT COUNT(definition) FROM Definition definition";
	private static final String _ORDER_BY_ENTITY_ALIAS = "definition.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Definition exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(DefinitionPersistenceImpl.class);
	private static final Definition _nullDefinition = new DefinitionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Definition> toCacheModel() {
				return _nullDefinitionCacheModel;
			}
		};

	private static final CacheModel<Definition> _nullDefinitionCacheModel = new CacheModel<Definition>() {
			@Override
			public Definition toEntityModel() {
				return _nullDefinition;
			}
		};
}