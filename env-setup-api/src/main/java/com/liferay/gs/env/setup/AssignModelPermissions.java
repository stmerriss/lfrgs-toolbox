package com.liferay.gs.env.setup;


import com.liferay.gs.env.setup.internal.AssignModelPermissionsImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.service.permission.ModelPermissions;

/**
 * @author Andrew Betts
 */
public interface AssignModelPermissions {

	interface AuditedModelGetter<M extends AuditedModel, A1, A2, A3, A4, A5> {

		M get(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5);

	}

	interface AuditedModelGetter_1<M extends AuditedModel, A1> {

		M get(A1 a1);

	}

	interface AuditedModelGetter_2<M extends AuditedModel, A1, A2> {

		M get(A1 a1, A2 a2);

	}

	interface AuditedModelGetter_3<M extends AuditedModel, A1, A2, A3> {

		M get(A1 a1, A2 a2, A3 a3);

	}

	interface AuditedModelGetter_4<M extends AuditedModel, A1, A2, A3, A4> {

		M get(A1 a1, A2 a2, A3 a3, A4 a4);

	}

	// add

	default  <M extends AuditedModel, A1> void addAuditedModelPermissions(
		long groupId, ModelPermissions modelPermissions,
		AssignModelPermissionsImpl.AuditedModelGetter_1<M, A1> getter, A1 arg1)
		throws PortalException {

		addAuditedModelPermissions(
			groupId, modelPermissions, getter.get(arg1));
	}

	default  <M extends AuditedModel, A1, A2> void addAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter_2<M, A1, A2> getter,
			A1 arg1, A2 arg2)
		throws PortalException {

		addAuditedModelPermissions(
			groupId, modelPermissions, getter.get(arg1, arg2));
	}

	default  <M extends AuditedModel, A1, A2, A3> void addAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter_3<M, A1, A2, A3> getter,
			A1 arg1, A2 arg2, A3 arg3)
		throws PortalException {

		addAuditedModelPermissions(
			groupId, modelPermissions, getter.get(arg1, arg2, arg3));
	}

	default  <M extends AuditedModel, A1, A2, A3, A4> void addAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter_4<M, A1, A2, A3, A4> getter,
			A1 arg1, A2 arg2, A3 arg3, A4 arg4)
		throws PortalException {

		addAuditedModelPermissions(
			groupId, modelPermissions,
			getter.get(arg1, arg2, arg3, arg4));
	}

	default  <M extends AuditedModel, A1, A2, A3, A4, A5> void addAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter<M, A1, A2, A3, A4, A5> getter,
			A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5)
		throws PortalException {

		addAuditedModelPermissions(
			groupId, modelPermissions,
			getter.get(arg1, arg2, arg3, arg4, arg5));
	}

	// remove

	default  <M extends AuditedModel, A1> void removeAuditedModelPermissions(
		long groupId, ModelPermissions modelPermissions,
		AssignModelPermissionsImpl.AuditedModelGetter_1<M, A1> getter, A1 arg1)
		throws PortalException {

		removeAuditedModelPermissions(
			groupId, modelPermissions, getter.get(arg1));
	}

	default  <M extends AuditedModel, A1, A2> void removeAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter_2<M, A1, A2> getter,
			A1 arg1, A2 arg2)
		throws PortalException {

		removeAuditedModelPermissions(
			groupId, modelPermissions, getter.get(arg1, arg2));
	}

	default  <M extends AuditedModel, A1, A2, A3> void removeAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter_3<M, A1, A2, A3> getter,
			A1 arg1, A2 arg2, A3 arg3)
		throws PortalException {

		removeAuditedModelPermissions(
			groupId, modelPermissions, getter.get(arg1, arg2, arg3));
	}

	default  <M extends AuditedModel, A1, A2, A3, A4> void removeAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter_4<M, A1, A2, A3, A4> getter,
			A1 arg1, A2 arg2, A3 arg3, A4 arg4)
		throws PortalException {

		removeAuditedModelPermissions(
			groupId, modelPermissions,
			getter.get(arg1, arg2, arg3, arg4));
	}

	default  <M extends AuditedModel, A1, A2, A3, A4, A5> void removeAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions,
			AssignModelPermissionsImpl.AuditedModelGetter<M, A1, A2, A3, A4, A5> getter,
			A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5)
		throws PortalException {

		removeAuditedModelPermissions(
			groupId, modelPermissions,
			getter.get(arg1, arg2, arg3, arg4, arg5));
	}

	public <M extends AuditedModel> void addAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions, M m)
		throws PortalException;

	public <M extends AuditedModel> void removeAuditedModelPermissions(
			long groupId, ModelPermissions modelPermissions, M m)
		throws PortalException;

}