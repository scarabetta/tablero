package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.obra.ObraJpaDao;
import ar.gob.buenosaires.dao.jpa.obra.ObraRepository;
import ar.gob.buenosaires.dao.jpa.obra.ObraRepositoryImpl;
import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.geocoder.adapter.response.DatoUtil;
import ar.gob.buenosaires.geocoder.adapter.response.DireccionNormalizada;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.adapter.response.SeccionManzanaParcela;
import ar.gob.buenosaires.geocoder.service.GeoCoderService;
import ar.gob.buenosaires.service.ComunaService;
import ar.gob.buenosaires.service.ObraService;

@Service
public class ObraServiceImpl implements ObraService {

	@Autowired
	private ObraRepository repository;

	@Autowired
	private GeoCoderService geoCoderService;

	@Autowired
	private ComunaService comunaService;

	@Override
	public Obra createObra(Obra obra) {
		return getObraJpaDao().save(obra);
	}

	@Override
	public Obra updateObra(Obra obra) {
		String direccion = "";
		if (obra.getDireccion() != null && !obra.getDireccion().isEmpty()) {
			direccion = obra.getDireccion();
	} else if (obra.getDireccionDesde() != null && !obra.getDireccionDesde().isEmpty()) {
			direccion = obra.getDireccionDesde();
		}
		if (!direccion.isEmpty()) {
			GeoCoderResponse geoCode = geoCoderService.getGeoCoding(direccion);
			List<DireccionNormalizada> direccionesNormalizadas = geoCode.getDireccionesNormalizadas();
			if (!direccionesNormalizadas.isEmpty()) {
				DireccionNormalizada direccionNormalizada = this.buscarDireccion(direccion, direccionesNormalizadas);
				obra.setUsigLatitud(direccionNormalizada.getCoordenadas().getY());
				obra.setUsigLongitud(direccionNormalizada.getCoordenadas().getX());

				DatoUtil datoUtil = geoCoderService.getDatoUtil(direccionNormalizada.getNombreCalle(), direccionNormalizada.getAltura()).getDatoUtil();
				if (datoUtil != null) {
					obra.setComuna(comunaService.getComunaPorNombre(datoUtil.getComuna()));
					obra.setUsigBarrio(datoUtil.getBarrio());
					// No lo vamos a cargar por ahora
					// obra.setUsigTransporteCercano(datoUtil.getTransporteCercano());
					obra.setUsigAreaHospitalaria(datoUtil.getAreaHospitalaria());
					obra.setUsigDistritoEscolar(datoUtil.getDistritoEscolar());
					obra.setUsigRegionSanitaria(datoUtil.getRegionSanitaria());
					obra.setUsigComisaria(datoUtil.getComisaria());
					obra.setUsigCodigoPostal(datoUtil.getCodigoPostal());
					obra.setUsigCPU(datoUtil.getCodigoDePlaneamientoUrbano());

					SeccionManzanaParcela smp = geoCoderService
							.getSeccionManzanaParcela(direccionNormalizada.getCodCalle(),
									direccionNormalizada.getAltura())
							.getSmp();
					if (smp != null) {
						obra.setUsigSeccion(smp.getSeccion());
						obra.setUsigManzana(smp.getManzana());
						obra.setUsigParcela(smp.getParcela());
					}
				}

			}
		}
		return getObraJpaDao().save(obra);
	}

	private DireccionNormalizada buscarDireccion(String direccion, List<DireccionNormalizada> direccionesNormalizadas) {
		for (DireccionNormalizada direccionNormalizada : direccionesNormalizadas) {
			if (direccionNormalizada.getDireccion().equalsIgnoreCase(direccion + ", caba")) {
				return direccionNormalizada;
			}
		}
		return direccionesNormalizadas.get(0);
	}

	@Override
	public void deleteObra(Long id) throws ESBException {
		Obra rol = getObraJpaDao().findOne(id);
		if (rol != null) {
			getObraJpaDao().delete(rol);
		} else {
			throw new ESBException(CodigoError.OBRA_INEXISTENTE.getCodigo(), "No se encontro la Obra con id: " + id);
		}
	}

	@Override
	public Obra getObraPorId(Long id) {
		return getObraJpaDao().findOne(id);
	}

	@Override
	public List<Obra> getObras() {
		return getObraJpaDao().findAll();
	}

	ObraJpaDao getObraJpaDao() {
		return repository.getObraJpaDao();
	}

	@VisibleForTesting
	public void setObraRepository(ObraRepositoryImpl repo) {
		this.repository = repo;
	}
}
