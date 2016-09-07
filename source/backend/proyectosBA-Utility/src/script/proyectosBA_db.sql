-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for proyectos_ba_generated
CREATE DATABASE IF NOT EXISTS `proyectos_ba_generated` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `proyectos_ba_generated`;


-- Dumping structure for table proyectos_ba_generated.jurisdiccion
CREATE TABLE IF NOT EXISTS `jurisdiccion` (
  `idJurisdiccion` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `abreviatura` varchar(50) NOT NULL,
  `mision` text NOT NULL,
  `codigo` varchar(50) NOT NULL,
  PRIMARY KEY (`idJurisdiccion`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.jurisdiccion: ~23 rows (approximately)
/*!40000 ALTER TABLE `jurisdiccion` DISABLE KEYS */;
INSERT INTO `jurisdiccion` (`idJurisdiccion`, `nombre`, `abreviatura`, `mision`, `codigo`) VALUES
	(1, 'Agencia Gubernamental de Control', 'AGC', 'Transformar las conductas del ciudadano incrementando su nivel de compromiso en el cumplimiento de \'las normas ciudadanas\' a través de un estado que administra un modelo de gestión de la convivencia con herramientas ágiles, transparentes e inteligentes.', 'AGC'),
	(50, 'Banco Ciudad de Buenos Aires', 'BCBA', 'Hacer foco en la banca social y la banca de desarrollo siendo rentables', 'BCBA'),
	(51, 'Ente Autarquico Teatro Colón', 'EATC', 'La misión del Teatro Colón es brindar una programación con un alto nivel de excelencia y calidad artística. El Teatro debe llevar a cabo este encargo velando por el interés cultural, artístico y social de su programa; permitiendo una profunda integración con la sociedad toda.', 'EATC'),
	(52, 'Ministerio de Ambiente y Espacio Público', 'MAYEPGC', '.', 'MAYEPGC'),
	(53, 'Ministerio de Cultura', 'MCGC', 'Repensar y promover la Cultura en la Ciudad con la mirada puesta en el Siglo XXI', 'MCGC'),
	(54, 'Ministerio de Desarrollo Humano y Habitat', 'MHYDHGC', 'Dar una respuesta integral y transversal a las diferentes problemáticas que condicionan la igualdad real de oportunidades y de trato en las distintas etapas de la vida, asegurando la integralidad de las prestaciones con eje en la familia, creando las condiciones que garanticen la inclusión social y la participación comunitaria, a través de la gestión social del hábitat.', 'MHYDHGC'),
	(55, 'Ministerio de Desarrollo Urbano y Transporte', 'MDUYTGC', '.', 'MDUYTGC'),
	(56, 'Ministerio de Educacion', 'MEGC', 'Transformar a la Ciudad de Buenos Aires en una ""Ciudad Educadora"" de calidad y orientada al futuro con escuelas que inviten a la inclusión, promuevan la igualdad de oportunidades y acompañen la entrada de los chicos al siglo XXI.', 'MEGC'),
	(57, 'Ministerio de Gobierno', 'MGOBGC', 'En ejercicio pleno de su autonomía, posicionar a la CABA como ejemplo de gestión transparente e innovadora, convivencia e integración metropolitana', 'MGOBGC'),
	(58, 'Ministerio de Hacienda', 'MHGC', 'Contribuir al desarrollo y concreción de los proyectos del gobierno mediante una gestión eficiente que garantice la generación de recursos, la optimización de gastos, la coordinación de recursos humanos y la simplificación de los procedimientos.', 'MHGC'),
	(59, 'Ministerio de Modernización, Innovación y Tecnología', 'MMIYTGC', '.', 'MMIYTGC'),
	(60, 'Ministerio de Salud', 'MSGC', 'Sistema de Salud en Red con priorización de la APS basado en las necesidades y la participación de la comunidad, brindando servicios de calidad en forma eficiente con incremento en los niveles de satisfacción del ciudadano.', 'MSGC'),
	(61, 'Secretaría Cultura Ciudadana y Función Pública', 'SECCCYFP', 'Promover los cambios culturales necesarios en la implementación de políticas públicas que contribuyan a tener una organización alineada con un servicio de calidad y la buena convivencia entre vecinos, a fin de lograr que BA sea el mejor lugar para vivir.', 'SECCCYFP'),
	(62, 'Secretaría de Descentralización', 'SECDES', 'Consolidar la presencia del Estado a través de Comunas abiertas, democráticas y participativas. Los pilares serán la cercanía y calidez en la atención al vecino y garantizar la constancia en la prosecución de los objetivos preestablecidos.', 'SECDES'),
	(63, 'Secretaría de Integración Social y Urbana - Villa 31', 'SECISYU', 'Diseñar estrategias, planes, políticas y proyectos vinculados a la integración urbana, social y económica Retiro – Puerto y de las Villas 31 y 31bis, coordinando pautas y acciones comunes con los actores comunitarios y otros organismos de orden municipal y nacional en el ejercicio del proyecto de urbanización y de inclusión socioeconómica; generando herramientas para que sus habitantes puedan desarrollar sus proyectos de vida', 'SECISYU'),
	(64, 'Secretaría General', 'SGYRI', 'Contribuir al logro del mejor gobierno de la historia de la Ciudad', 'SGYRI'),
	(65, 'Sindicatura General de la Ciudad de Buenos Aires', 'SGCBA', 'Acompañar la gestión de gobierno, asegurando un Sistema de Control Interno que genere y consolide todas las medidas de gobierno para gestionar anticipadamente los riesgos  de la administración y aumentar las probabilidades de alcanzar los objetivos y metas establecidas por el Jefe de Gobierno en los aspectos normativos y transparencia de la gestión.', 'SGCBA'),
	(66, 'Subsecretaría de Comunicación', 'SSCOMUNIC', 'Cominicación directa y personalizada con el vecino.', 'SSCOMUNIC'),
	(67, 'Subsecretaría de Contenidos', 'SSCON', 'Lograr una buena percepción, por parte de los vecinos, de la Ciudad y de su Gobierno', 'SSCON'),
	(68, 'Subsecretaría de Coordinación y Promoción de Eventos', 'SSCYPE', 'Posicionar a la Ciudad como polo generador de eventos unicos gratuitos para los vecinos, que fomenten el encuentro, la participación y la concientización.', 'SSCYPE'),
	(69, 'Subsecretaría de Procesos y Sistemas', 'SSSYP', '.', 'SSSYP'),
	(70, 'Subsecretaría Demanda Ciudadana, Calidad y Cercanía', 'SSDCCYC', 'Escuchar, Conocer e Interpretar al Ciudadano,  sus Demandas y Necesidades desde una fuerte Política de Cercanía, con un Seguimiento y Control de la Respuesta que damos como Gobierno, midiendo su Calidad y la Satisfacción del vecino.', 'SSDCCYC'),
	(71, 'Vicejefatura de Gobierno', 'AVJG', '.', 'AVJG');
/*!40000 ALTER TABLE `jurisdiccion` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.objetivo_jurisdiccional
CREATE TABLE IF NOT EXISTS `objetivo_jurisdiccional` (
  `idObjetivoJurisdiccional` int(11) NOT NULL AUTO_INCREMENT,
  `idJurisdiccion` int(11) DEFAULT NULL,
  `codigo` varchar(50) NOT NULL,
  `nombre` varchar(512) NOT NULL,
  `idJurisdiccionAux` int(11) NOT NULL,
  PRIMARY KEY (`idObjetivoJurisdiccional`),
  KEY `FK_jurisdiccion_objetoJurisdiccional` (`idJurisdiccion`),
  CONSTRAINT `FK_jurisdiccion_objetoJurisdiccional` FOREIGN KEY (`idJurisdiccion`) REFERENCES `jurisdiccion` (`idJurisdiccion`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.objetivo_jurisdiccional: ~120 rows (approximately)
/*!40000 ALTER TABLE `objetivo_jurisdiccional` DISABLE KEYS */;
INSERT INTO `objetivo_jurisdiccional` (`idObjetivoJurisdiccional`, `idJurisdiccion`, `codigo`, `nombre`, `idJurisdiccionAux`) VALUES
	(21, 1, 'AGC.1', 'Integrar todos los Organismos Administrativos Permisionarios en un Organismo único de Registración y Normalización de datos (permisos y habilitaciones productivas y comerciales) desarrollando un único canal de interacción con el vecino que agilice los servicios otorgados por la Agencia y los dote de mayor transparencia.', 0),
	(22, 1, 'AGC.2', 'Fortalecer un modelo de inspección amplio e inteligente, integrado con el esquema sancionatorio que acompañe al vecino a desarrollar una conducta apegada a las normas de buena convivencia.', 0),
	(23, 1, 'AGC.3', 'Aumentar la proximidad ciudadana a traves de un modelo participativo y transparente', 0),
	(24, 50, 'BCBA.1', 'Ampliar la llegada de la propuesta de valor de los productos y servicios del Banco Ciudad a una mayor cantidad de ciudadanos', 0),
	(25, 50, 'BCBA.2', 'Acompañar y propiciar el desarrollo de empresas, con foco en las Pymes y Mypes', 0),
	(26, 50, 'BCBA.3', 'Lograr una rentabilidad sustentable de largo plazo para el Banco', 0),
	(27, 50, 'BCBA.4', 'Sostener el crédito a largo plazo ofreciendo alternativas de financiamiento accesibles a las familias', 0),
	(28, 51, 'EATC.1', 'Ofrecer una temporada artística de nivel y de calidad', 0),
	(29, 51, 'EATC.2', 'Profundizar la integración del Teatro con la sociedad toda', 0),
	(30, 51, 'EATC.3', 'Modernizar la gestión del Teatro', 0),
	(31, 51, 'EATC.4', 'Promover el desarrollo de las generaciones emergentes en la formación de artes teatrales', 0),
	(32, 63, 'SECISYU.1', 'Promover la interconectividad e integración entre el barrio 31 y 31 bis, la zona portuaria y la ciudad', 0),
	(33, 63, 'SECISYU.2', 'Generar un sistema económico sustentable', 0),
	(34, 63, 'SECISYU.3', 'Garantizar condiciones de habitabilidad dignas para los residentes', 0),
	(35, 63, 'SECISYU.4', 'Promover un mejoramiento de la calidad de vida, educación y acceso a los servicios sociales para los habitantes del barrio', 0),
	(36, 52, 'MAYEPGC.1', 'Realizar obras de Regeneración Urbana con el objetivo de lograr una ciudad más inclusiva y sustentable, en constante diálogo con el arte y la innovación.', 0),
	(37, 52, 'MAYEPGC.2', 'Que la ciudadanía acceda a un Espacio Público conservado y accesible, asegurando una mejor calidad de vida a la población.', 0),
	(38, 52, 'MAYEPGC.3', 'Asegurar el cumplimiento de la normativa vigente  y regular así el Uso del Espacio Público priorizando los intereses ciudadanos y contribuyendo en la puesta en valor de los espacios comunes.', 0),
	(39, 52, 'MAYEPGC.4', 'Que la Ciudad de Buenos Aires sea una ciudad más Limpia.', 0),
	(40, 52, 'MAYEPGC.5', 'Mejora la calidad ambiental y efectos sobre cambio climático colaborando en la recomposición de los Recursos Naturales afectados y el el mejoramiento del uso del Espacio Público.', 0),
	(41, 53, 'MCGC.1', 'Potenciar, revitalizar y transformar los programas artístico-culturales en las comunas', 0),
	(42, 53, 'MCGC.2', 'Apuntalar la cultura pública no estatal', 0),
	(43, 53, 'MCGC.3', 'Optimizar la línea de cultura en la calle', 0),
	(44, 53, 'MCGC.4', 'Visibilizar expresiones culturales jóvenes', 0),
	(45, 53, 'MCGC.5', 'Refuncionalizar los efectores públicos', 0),
	(46, 54, 'MHYDHGC.1', 'Respuesta ante las situaciones de emergencia social', 0),
	(47, 54, 'MHYDHGC.2', 'Asegurar la integralidad y eficacia de las prestaciones', 0),
	(48, 54, 'MHYDHGC.3', 'Crear las condiciones que garanticen la inclusión social y la participación comunitaria a través de la gestión social del Hábitat en villas, asentamientos y su entorno inmediato.', 0),
	(49, 54, 'MHYDHGC.4', 'Garantizar la Igualdad de Oportunidades', 0),
	(50, 55, 'MDUYTGC.1', 'Continuar con la implementación del Plan Hidráulico', 0),
	(51, 55, 'MDUYTGC.2', 'Promover el desarrollo sostenible de la ciudad de Buenos Aires', 0),
	(52, 55, 'MDUYTGC.3', 'Mejorar el servicio de Subterraneos', 0),
	(53, 55, 'MDUYTGC.4', 'Mejorar el transporte público en superficie', 0),
	(54, 55, 'MDUYTGC.5', 'Implementar una mirada de desarrollo urbanistico integral', 0),
	(55, 55, 'MDUYTGC.6', 'Incluir el desarrollo de los vecinos dentro de la planificación urbanistica de la ciudad', 0),
	(56, 55, 'MDUYTGC.7', 'Mejorar el acceso a la vivienda ', 0),
	(57, 55, 'MDUYTGC.8', 'Incrementar la Igualdad de oportunidades', 0),
	(58, 55, 'MDUYTGC.9', 'Desarrollo de la movilidad para mejorar la vida de los vecinos', 0),
	(59, 55, 'MDUYTGC.10', 'Fomentar la ejecución de proyectos público-privados', 0),
	(60, 56, 'MEGC.1', 'Mejorar la Calidad Educativa', 0),
	(61, 56, 'MEGC.2', 'Asegurar la equidad educativa', 0),
	(62, 56, 'MEGC.3', 'Asegurar la sustentabilidad del sistema educativo', 0),
	(63, 56, 'MEGC.4', 'Orientar la escuela hacia el futuro', 0),
	(64, 56, 'MEGC.5', 'Promover el concepto de Ciudad Educadora', 0),
	(65, 57, 'MGOBGC.1', 'Creación de una cultura metropolitana que sea ejemplo de convivencia y gestión responsable, abierta e innovadora', 0),
	(66, 57, 'MGOBGC.2', 'Desarrollar en un sistema equilibrado el tratamiento y la disposición final de residuos sólidos urbanos del Área Metropolitana propendiendo a una mayor generación de Energía Limpia', 0),
	(67, 57, 'MGOBGC.3', 'Fortalecer el ejercicio pleno de la autonomía', 0),
	(68, 58, 'MHGC.1', 'Impulsar un plan integral de optimización de gastos e ingresos mediante la detección continua de oportunidades de ahorros y generación de otros ingresos y su correspondiente implementación de acciones que permitan su aplicación en inversiones.', 0),
	(69, 58, 'MHGC.2', 'Consolidar alternativas de financiamiento de corto y largo plazo destinadas a cubrir necesidades transitorias y proyectos estratégicos garantizando un perfil de deuda sostenible para la Ciudad Autónoma de Buenos Aires.', 0),
	(70, 58, 'MHGC.3', 'Profundizar la simplificación de procedimientos mediante la revisión de procesos y la incorporación de nuevas herramientas tecnológicas que garanticen eficiencia y agilidad en la gestión operativa, económica y financiera, y en la interacción con ciudadanos, proveedores y clientes internos.', 0),
	(71, 58, 'MHGC.4', 'Contribuir al desarrollo de servidores públicos comprometidos y orientados a brindar servicios de calidad a los ciudadanos, mediante estructuras organizativas y dotaciones ajustadas a las necesidades de la gestión.', 0),
	(72, 58, 'MHGC.5', 'Desarrollar un esquema de programación económica que permita generar mayor calidad de información para la toma de decisiones.', 0),
	(73, 58, 'MHGC.6', 'Promover el crecimiento sostenido de los ingresos tributarios mediante la implementación de prácticas innovadoras y eficaces de recaudación y el desarrollo de acciones que profundicen el vínculo con el contribuyente simplificando y agilizando sus gestiones.', 0),
	(74, 59, 'MMIYTGC.1', 'Promover la sustentabilidad como eje esencial de una Ciudad Inteligente.', 0),
	(75, 59, 'MMIYTGC.2', 'Duplicar el crecimiento del Turismo en CABA', 0),
	(76, 59, 'MMIYTGC.3', 'Hacer de la innovación un eje central del Gobierno de la Ciudad de Buenos Aires', 0),
	(77, 59, 'MMIYTGC.4', 'Impulsar y gestionar la infraestructura humana y urbana que nos permita celebrar con éxito los Juegos Olímpicos de la Juventud', 0),
	(78, 59, 'MMIYTGC.5', 'Construir un ecosistema local que motorice la innovación y potencie el crecimiento de emprendimientos y Pymes', 0),
	(79, 59, 'MMIYTGC.6', 'Convertir a CABA en símbolo y referente global de talento humano.', 0),
	(80, 59, 'MMIYTGC.7', 'Convertir a Bs As en referente mundial de emprendimiento y desarrollo emprendedor', 0),
	(81, 59, 'MMIYTGC.8', 'Fortalecer a las industrias estratégicas de la Ciudad e impulsar su crecimiento', 0),
	(82, 59, 'MMIYTGC.9', 'Impulsar reformas en el sistema público que permitan institucionalizar las nuevas formas de generación económica en la ciudad', 0),
	(83, 59, 'MMIYTGC.10', 'Generar oportunidades de inclusión y desarrollo humano a partir del emprendimiento y el trabajo productivo, en articulación con los diferentes actores sociales que componen la ciudad.', 0),
	(84, 59, 'MMIYTGC.11', 'Empoderar a la ciudadanía joven a través del deporte', 0),
	(85, 59, 'MMIYTGC.12', 'Contribuir al desarrollo urbano y tecnológico priorizando el sur de CABA', 0),
	(86, 59, 'MMIYTGC.13', 'Potenciar a CABA como la plaza de inversión más atractiva de LATAM', 0),
	(87, 59, 'MMIYTGC.14', 'Promover instrumentos de innovación financiera con impacto social', 0),
	(88, 59, 'MMIYTGC.15', 'Crear un espacio de convivencia saludable para las Relaciones Laborales', 0),
	(89, 59, 'MMIYTGC.16', 'Hacer que esté bueno trabajar en Buenos Aires', 0),
	(90, 59, 'MMIYTGC.17', 'Generación de recursos genuinos para proyectos sociales', 0),
	(91, 59, 'MMIYTGC.18', 'Desarrollar soluciones digitales que potencien la cercanía entre la Ciudad y los ciudadanos.', 0),
	(92, 59, 'MMIYTGC.19', 'Posicionar a Buenos Aires entre las 10 ciudades mas atractivas del mundo', 0),
	(93, 59, 'MMIYTGC.20', 'Utilizar la tecnología como herramienta democratizadora.', 0),
	(94, 59, 'MMIYTGC.21', 'Fortalecer la información estadística y definir los perfiles de turistas', 0),
	(95, 59, 'MMIYTGC.22', 'Rediseñar las Experiencias Turísticas en la Ciudad', 0),
	(96, 60, 'MSGC.1', 'Mejorar y ampliar sistema de información y comunicación', 0),
	(97, 60, 'MSGC.2', 'Acuerdos y planificación intersectorial', 0),
	(98, 60, 'MSGC.3', 'Fortalecer la red pública de cuidados integrales', 0),
	(99, 60, 'MSGC.4', 'Fortalecer y perfilar la red de hospitales', 0),
	(100, 60, 'MSGC.5', 'Mejorar desempeño económico', 0),
	(101, 61, 'SECCCYFP.1', 'Lograr una organización alineada con el valor del servicio, que se vea reflejada en cada “momento de verdad” con el vecino.', 0),
	(102, 61, 'SECCCYFP.2', 'Lograr que en Buenos Aires los vecinos seamos protagonistas de una Ciudad que disfruta del encuentro y convivencia en el espacio público.', 0),
	(103, 62, 'SECDES.1', 'Asistir a las comunas para que cumplan con sus objetivos.', 0),
	(104, 62, 'SECDES.2', 'Acercar la comuna, como primera instancia de gobierno, al vecino.', 0),
	(105, 62, 'SECDES.3', 'Empoderar a la Comuna como principal comunicador de las acciones dentro de su territorio.', 0),
	(106, 62, 'SECDES.4', 'Alcanzar un nivel óptimo de calidad en la prestación de servicios en las 15 Comunas.', 0),
	(107, 62, 'SECDES.5', 'Superar los estándares de eficiencia actuales.', 0),
	(108, 64, 'SGYRI.1', 'Proyectar internacionalmente la ciudad, la gestión de gobierno y la figura del jefe de gobierno.', 0),
	(109, 64, 'SGYRI.2', 'Impulsar un plan sistemático de relaciones del gobierno con actores políticos, sociales y privados', 0),
	(110, 64, 'SGYRI.3', 'Apoyar las prioridades estratégicas de gobierno y agendas transversales e interjurisdiccionales', 0),
	(111, 65, 'SGCBA.1', 'Estandarización, Fortalecimiento y Madurez del Control Interno de todas las Áreas del Gobierno de la Ciudad de Buenos Aires', 0),
	(112, 65, 'SGCBA.2', 'Fortalecimiento del vínculo de Control Interno con los Ministerios', 0),
	(113, 65, 'SGCBA.3', 'Eficiencia y buenas prácticas', 0),
	(114, 66, 'SSCOMUNIC.1', 'Presupuesto transversal a todos los proyectos.', 0),
	(115, 66, 'SSCOMUNIC.2', 'Monitorear el estado de la opinión pública.', 0),
	(116, 66, 'SSCOMUNIC.3', 'Comunicar las acciones del Gobierno y del Jefe de Gobierno utilizando las más modernas herramientas de comunicación directa y digital.', 0),
	(117, 66, 'SSCOMUNIC.4', 'Fomentar la participación ciudadana y generar cercanía con el vecino.', 0),
	(118, 67, 'SSCON.1', 'Colaborar en el posicionamiento del GCBA y el Jefe de Gobierno mediante la generación de contenidos de comunicación.', 0),
	(119, 68, 'SSCYPE.1', 'Canalizar las distintas campañas de concientización y participación de los distintos Ministerios en cada evento.', 0),
	(120, 69, 'SSSYP.1', 'Fortalecer los procesos operativos y de gestión, transversales y verticales, para generar eficiencias en la gestión interna y en el servicio al Ciudadano.', 0),
	(121, 69, 'SSSYP.2', 'Fortalecer las capacidades de la Infraestructura para satisfacer los niveles de servicio y disponibilidad de los Sistemas.', 0),
	(122, 69, 'SSSYP.3', 'Continuar con el fortalecimiento de la seguridad del acceso y custodia de los activos informáticos (Redes y Centro de Datos, Sistemas, Servicios).', 0),
	(123, 69, 'SSSYP.4', 'Adoptar un Modelo de Entrega de Servicios que mejore la calidad de entrega de los mismos, acordando y comprometiendo Acuerdos de Niveles de Servicio.', 0),
	(124, 70, 'SSDCCYC.1', 'Mejorar la Calidad de Atencion en todas las áreas que brindan servicios, por medio de la optimización de los sistemas de información y la promoción de la autogestión.', 0),
	(125, 70, 'SSDCCYC.2', 'Mejorar la Experiencia del Ciudadano en su interacción con el GCBA Eficientizando los tiempos, procesos  e infraestructura de todos los servicios.', 0),
	(126, 70, 'SSDCCYC.3', 'Lograr la resolución de los reclamos relacionados al Mantenimiento del Espacio Público, Garantizando el cumplimiento de los SLA establecidos y la satisfacción del Vecino', 0),
	(127, 70, 'SSDCCYC.4', 'Medir, evaluar y asegurar la calidad de la respuesta del gobierno a las demandas de la ciudania, y acciones de gobierno, bajo estándares de sustentabilidad (eficiencia, armonia y perdurabilidad) y satisfacción del vecino. Certificar estándares internacionales de gestión de calidad y satisfacción', 0),
	(128, 70, 'SSDCCYC.5', 'Proteger y promover los derechos de los consumidores, facilitando la solución de sus controversias con altos estándares de calidad, eficiencia e innovación, acercando los servicios a la población.', 0),
	(129, 71, 'AVJG.1', 'Promover las relaciones interjurisdiccionales', 0),
	(130, 71, 'AVJG.2', 'Posicionar a Buenos Aires como la ciudad con mejores indicadores en conductas saludables de la juventud en Latinoamérica', 0),
	(131, 71, 'AVJG.3', 'Proporcionar una visión integral de las problemáticas de la juventud de la Ciudad', 0),
	(132, 71, 'AVJG.4', 'Promover las distintas expresiones de la juventud aumentando su participación y relevancia en la vida de los vecinos de la Ciudad', 0),
	(133, 71, 'AVJG.5', 'Estimular la formación vocacional y profesional de los jóvenes de la Ciudad desde un abordaje integrador', 0),
	(134, 71, 'AVJG.6', 'Promover la actividad física como derecho y contenido de la calidad de vida de toda la población', 0),
	(135, 71, 'AVJG.7', 'Posicionar a la Ciudad de Buenos Aires como referente en la defensa y promoción de los Derechos Humanos; haciendo eje en la convivencia, el diálogo, el encuentro, la inclusión y el pluralismo cultural', 0),
	(136, 71, 'AVJG.8', 'Desarrollo gastronómico de la Ciudad de Buenos Aires', 0),
	(137, 71, 'AVJG.9', 'Prevención de obesidad y sobrepeso infantil; promoción de hábitos saludables en la población.', 0),
	(138, 71, 'AVJG.10', 'Promoción de la gastronomía como hito económico y cultural', 0),
	(139, 71, 'AVJG.11', 'Desarrollo familiar de los vecinos de la ciudad', 0),
	(140, 71, 'AVJG.12', 'Instalar a la familia como valor social', 0);
/*!40000 ALTER TABLE `objetivo_jurisdiccional` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.indicador_estrategico
CREATE TABLE IF NOT EXISTS `indicador_estrategico` (
  `idIndicadorEstrategico` int(11) NOT NULL AUTO_INCREMENT,
  `idObjetivoJurisdiccional` int(11) DEFAULT NULL,
  `indicador` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idIndicadorEstrategico`),
  KEY `FK_objetivoJurisdiccional_indicardorEstrategico` (`idObjetivoJurisdiccional`),
  CONSTRAINT `FK_objetivoJurisdiccional_indicardorEstrategico` FOREIGN KEY (`idObjetivoJurisdiccional`) REFERENCES `objetivo_jurisdiccional` (`idObjetivoJurisdiccional`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.indicador_estrategico: ~2 rows (approximately)
/*!40000 ALTER TABLE `indicador_estrategico` DISABLE KEYS */;
INSERT INTO `indicador_estrategico` (`idIndicadorEstrategico`, `idObjetivoJurisdiccional`, `indicador`, `descripcion`) VALUES
	(1, 23, 'indicador test', 'descripcion test'),
	(2, 23, 'indicador test 2', 'descripcion test 2');
/*!40000 ALTER TABLE `indicador_estrategico` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.objetivo_operativo
CREATE TABLE IF NOT EXISTS `objetivo_operativo` (
  `idObjetivoOperativo` int(11) NOT NULL AUTO_INCREMENT,
  `idObjetivoJurisdiccional` int(11) DEFAULT NULL,
  `codigo` varchar(50) NOT NULL,
  `nombre` varchar(512) NOT NULL,
  `idObjetivoJurisdiccionalAux` int(11) DEFAULT NULL,
  PRIMARY KEY (`idObjetivoOperativo`),
  KEY `FK_objetivoJurisdiccional_objetivoOperativo` (`idObjetivoJurisdiccional`),
  CONSTRAINT `FK_objetivoJurisdiccional_objetivoOperativo` FOREIGN KEY (`idObjetivoJurisdiccional`) REFERENCES `objetivo_jurisdiccional` (`idObjetivoJurisdiccional`)
) ENGINE=InnoDB AUTO_INCREMENT=392 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.objetivo_operativo: ~371 rows (approximately)
/*!40000 ALTER TABLE `objetivo_operativo` DISABLE KEYS */;
INSERT INTO `objetivo_operativo` (`idObjetivoOperativo`, `idObjetivoJurisdiccional`, `codigo`, `nombre`, `idObjetivoJurisdiccionalAux`) VALUES
	(22, 21, 'AGC.1.1', 'Consolidar y agilizar el tratamiento digital de los procesos de habilitación y registro', 21),
	(23, 21, 'AGC.1.2', 'Redefinir normas de seguridad a fin de regularizar las actividades en función de su realidad y problemática actual', 21),
	(24, 21, 'AGC.2.1', 'Centralizar y coordinar los esfuerzos inspectivos (Poder de Policía) del GCABA en un solo ámbito de aplicación a fin de llevar adelante una política Gubernamental unificada en materia inspectiva.', 21),
	(25, 21, 'AGC.2.2', 'Incrementar la calidad de las inspecciones, incorporando nuevas herramientas de gestión y mejorando las existentes.', 21),
	(26, 21, 'AGC.3.1', 'Realizar acciones de prevención y cercania con el vecino', 21),
	(27, 36, 'MAYEPGC.1.1', 'Areas Ambientales: Revitalizar el espacio público a través de un enfoque integral, fortaleciendo la identidad de los barrios, concentrándonos en la caminabilidad, en la sustentabilidad y en la puesta en valor del patrimonio existente', 36),
	(28, 36, 'MAYEPGC.1.2', 'Entornos Urbanos: Ejecución de proyectos de menor escala y con características particulares a lo largo de toda la ciudad, que buscan la revitalización de sectores degradados.', 36),
	(29, 36, 'MAYEPGC.1.3', 'Parques Uranos: Puesta en valor de varios de los grandes parques existentes; la creación de nuevos parques y plazas, contemplando la relación de la ciudad con el río, incorporando equipamiento para personas de todas las edades, incentivando la actividad física y acercando más espacios verdes a todos los barrios.', 36),
	(30, 36, 'MAYEPGC.1.4', 'Avenidas y Centros Comerciales a Cielo Abierto: Articular y coordinar la participación de los distintos sectores (público/privado) con el fin de mejorar la calidad del Espacio Público y estimular el crecimiento económico.', 36),
	(31, 37, 'MAYEPGC.2.1', 'Contribuir en la mejora continua asegurando a la ciudadanía la transitabilidad de la ciudad y la accesibilidad de los espacios comunes.', 37),
	(32, 37, 'MAYEPGC.2.2', 'Que la ciudadanía acceda a espacios públicos de esparcimiento mantenidos y de calidad.', 37),
	(33, 37, 'MAYEPGC.2.3', 'Que la ciudad cuente con un sistema lumínico eficiente, y en pos del ahorro energético.', 37),
	(34, 37, 'MAYEPGC.2.4', 'Que la ciudad cuente con sistemas pluviales capaces de afrontar potenciales inundaciones.', 37),
	(35, 37, 'MAYEPGC.2.5', 'Asegurar el gestionamiento, el  mantenimiento,  remodelación  y  reparación de los  edificios, equipamientos e instalaciones de los Cementerios  dependientes  del  Gobierno de la Ciudad Autónoma de Buenos Aires', 37),
	(36, 38, 'MAYEPGC.3.1', 'Contribuir al desarrollo de una ciudad a escala humana a partir del acceso a servicios de calidad en materia de uso del espacio público.', 38),
	(37, 38, 'MAYEPGC.3.2', 'Ejercer el poder de policía reduciendo la venta ilegal de bienes de uso para asegurar que los ciudadanos accedan a bienes adecuadamente regulados.', 38),
	(38, 38, 'MAYEPGC.3.3', 'Fortalecer la identidad ciudadana  a través del fomento de actividades artesanales y de producción de bienes de primera necesidad. Así como, fomentar y mejorar la oferta de bienes de primera necesidad.', 38),
	(39, 38, 'MAYEPGC.3.4', 'Arte en la Ciudad. Contribuir en la revalorización de la ciudad, brindando espacios a la ciudadanía para el arte y la recreación.', 38),
	(40, 39, 'MAYEPGC.4.1', 'IMPLEMENTAR UN SISTEMA DE RECICLADO EFICIENTE', 39),
	(41, 39, 'MAYEPGC.4.2', 'OPTIMIZAR EL SISTEMA DE RECOLECCIÓN DE RESIDUOS HÚMEDOS', 39),
	(42, 39, 'MAYEPGC.4.3', 'POTENCIAR EL EHU', 39),
	(43, 39, 'MAYEPGC.4.4', 'OPTIMIZAR EL MONITOREO Y CONTROL DEL SISTEMA DE HIGIENE URBANA', 39),
	(44, 39, 'MAYEPGC.4.5', 'REDUCIR LA DISPOSICIÓN FINAL DE RESIDUOS', 39),
	(45, 39, 'MAYEPGC.4.6', 'DESARROLLAR UN PLAN DE GESTIÓN INTEGRADO CONSIDERANDO EL NUEVO ESCENARIO AMBA', 39),
	(46, 40, 'MAYEPGC.5.1', 'Sostener y desarrollar una GIRSU ambientalmente adecuada, que promueva el uso eficiente de recursos', 40),
	(47, 40, 'MAYEPGC.5.2', 'Implementar una RED de monitoreo.', 40),
	(48, 40, 'MAYEPGC.5.3', 'Mitigar el Riesgo Ambiental', 40),
	(49, 40, 'MAYEPGC.5.4', 'Jerarquizar al Gobierno de la Ciudad de Buenos Aires como Referente a Nivel Nacional sobre temas ambientales', 40),
	(50, 40, 'MAYEPGC.5.5', 'Reducción de Consumo Eléctrico en Población Vulnerable', 40),
	(51, 40, 'MAYEPGC.5.6', 'Generar información de base para la elaboración de estrategias de mitigación y adaptación', 40),
	(52, 40, 'MAYEPGC.5.7', 'Recomponer sitios e industrias de la cuenca del Riachuelo', 40),
	(53, 40, 'MAYEPGC.5.8', 'Mejorar la calidad de los procesos administrativos y de resguardo de documentación.', 40),
	(54, 40, 'MAYEPGC.5.9', 'Controlar la gestión con ratios y alertas', 40),
	(55, 40, 'MAYEPGC.5.10', 'Asegurar el cumplimiento de la normativa vigente, mejorar el uso del espacio público y realizar campañas de concientización sobre la tenencia responsable de mascotas', 40),
	(56, 24, 'BCBA.1.1', 'Expandir los canales de atención', 24),
	(57, 24, 'BCBA.1.2', 'Consolidar y ampliar el desarrollo de los canales virtuales', 24),
	(58, 24, 'BCBA.1.3', 'Desarrollar la oferta de servicios y productos para individuos', 24),
	(59, 24, 'BCBA.1.4', 'Mejorar la experiencia del cliente', 24),
	(60, 24, 'BCBA.1.5', 'Mejorar los canales de Recaudación', 24),
	(61, 24, 'BCBA.1.6', 'Integridad y Apoyo a la Comunidad', 24),
	(62, 25, 'BCBA.2.1', 'Aumentar la cantidad de clientes y el cross-selling de empresas, ofreciendo servicios y productos activos y pasivos', 25),
	(63, 26, 'BCBA.3.1', 'Maximizar los ingresos', 26),
	(64, 27, 'BCBA.4.1', 'Colocar préstamos hipotecarios', 27),
	(65, 28, 'EATC.1.1', 'Garantizar la diversidad en la programación con eventos de excelencia para todos los públicos', 28),
	(66, 29, 'EATC.2.1', 'Incentivar la incorporación de nuevos públicos al Teatro', 29),
	(67, 30, 'EATC.3.1', 'Desarrollar planes que permitan captar recursos, a fin de obtener una mayor rentabilidad económica', 30),
	(68, 30, 'EATC.3.2', 'Mejorar la eficacia y eficiencia de la gestión, y la calidad del servicio', 30),
	(69, 30, 'EATC.3.3', 'Establecer un programa de RRHH', 30),
	(70, 30, 'EATC.3.4', 'Mejorar los servicios básicos del Ente Autárquico Teatro Colón', 30),
	(71, 31, 'EATC.4.1', 'Fomentar el trabajo pedagógico en las artes escénicas', 31),
	(72, 114, 'SSCOMUNIC.1.1', 'Presupuesto transversal a todos los proyectos', 114),
	(73, 115, 'SSCOMUNIC.2.1', 'Generar la información que sea necesaria para conocer la realidad ciudadana y la gestión de Gobierno. .-Apoyo al diseño de estrategias de gestión y comunicación, convirtiendo datos en información y herramientas.', 115),
	(74, 116, 'SSCOMUNIC.3.1', 'Utilizar tecnologías que permitan comunicar los diferentes proyectos a audiencias específicas, generando segmentaciones únicas interesadas en políticas públicas particulares. .-Obtener el mayor detalle sobre el público en cuestión para generar contenidos que mejoren la cercanía del Jefe de Gobierno a partir de la publicidad Online.', 116),
	(75, 116, 'SSCOMUNIC.3.2', 'A partir de las propuestas y necesidades de los vecinos, reforzar el vínculo de cercanía entre ellos y los funcionarios del Gobierno.', 116),
	(76, 116, 'SSCOMUNIC.3.3', 'Informar a los vecinos del área metropolitana sobre las acciones conjuntas entre sus Municipios y el Gobierno de la Ciudad.', 116),
	(77, 116, 'SSCOMUNIC.3.4', 'Mejorar la comprensión de las conversaciones que se producen en el mundo digital. .-Detectar posibles crisis y oportunidades de comunicación. .-Monitorear el estado de la opinión pública.', 116),
	(78, 117, 'SSCOMUNIC.4.1', 'Mejorar y ser pro-activos en la comunicación con los vecinos en base a la información que nos dejan en su interacción con el Gobierno.', 117),
	(79, 117, 'SSCOMUNIC.4.2', 'Incrementar el número de Vecinos de la Ciudad que participan del diseño, realización y seguimiento de las obras, proyectos y demás iniciativas gubernamentales.', 117),
	(80, 118, 'SSCON.1.1', 'Agendas Públicas: Posicionar al GCBA y a su Jefe de Gobierno ante todo evento público y actividades de gobierno.', 118),
	(81, 118, 'SSCON.1.2', 'Publicidad: Definir y desarrollar la Estrategia General de Comunicación del Gobierno en su totalidad, definiendo los ejes, valores, filtros, tono y focos.             Diseño de la planificación anual de campañas y su consecuente estrategia de medios y canales de comunicación.', 118),
	(82, 118, 'SSCON.1.3', 'Marca Ciudad: Crear y posicionar la imagen institucional de la Ciudad a nivel local, regional e  internacional.', 118),
	(83, 118, 'SSCON.1.4', 'Usina de información: Centralizar información de gestión de los Ministerios y de actos y anuncios públicos de Gobierno', 118),
	(84, 118, 'SSCON.1.5', 'Contenido Discursivo: Construcción, unificación y análisis del discurso del Jefe de Gobierno y voceros del Gobierno', 118),
	(85, 118, 'SSCON.1.6', 'Producción de contenidos: Desarrollar las piezas de comunicación que respondan a las necesidades de las Subsecretarias de Contenidos y de Comunicación y de los Ministerios y Entes del GCBA', 118),
	(86, 118, 'SSCON.1.7', 'Planificación de eventos: Definir y capitalizar los eventos que se celebran en la Ciudad que fortalezcan los ejes y posicionamiento del Gobierno.', 118),
	(87, 118, 'SSCON.1.8', 'Agencias. Contar con el asesoramiento necesario para el correcto desenvolvimiento de las actividades de la Subsecretaría.', 118),
	(88, 119, 'SSCYPE.1.1', 'Laten Argentinos.', 119),
	(89, 119, 'SSCYPE.1.2', 'Misa Criolla. INTEGRACION SOCIAL Involucrar a las personas màs alla de sus creencias, de su raza u origen, respetando la libertad de culto. En el marco de la Imponente Catedral de Buenos Aires los asistentes presenciaran la importante misa criolla.  integrando y fomentando los valores de convivencia y tolerancia.', 119),
	(90, 119, 'SSCYPE.1.3', 'Gala de música tropical. EJE DE DISFRUTE      Reivindicar a la musica tropical como uno de los principales generos  dentro de la cultura popular teniendo en cuenta el eje de cercanía.', 119),
	(91, 119, 'SSCYPE.1.4', 'Tributo a Rodrigo. INTEGRACION SOCIAL Homenajear al maximo exponente del cuarteto en Parque Centenario.', 119),
	(92, 119, 'SSCYPE.1.5', 'Valeria Lynch sinfónico. INTEGRACION SOCIAL      Llegar al publico adulto mediante el show masivo de esta reconocida artista teniendo en cuenta el eje de cercanía.', 119),
	(93, 119, 'SSCYPE.1.6', 'ONG Latinas. INTEGRACION SOCIAL', 119),
	(94, 119, 'SSCYPE.1.7', 'Festival a reírse. EJE DE DISFRUTE. Los aficionados al género del humor tendrán su festival, que congrega a los principales cómicos del país teniendo en cuenta el eje de alegría/disfrute.', 119),
	(95, 119, 'SSCYPE.1.8', 'Festival mundo verde. EJE DE DISFRUTE. Música, cine y talleres gratuitos, centrados en un  Festival cultural dedicado a concientizar sobre el consumo responsable y el cuidado del medio ambiente.ofreciendo entretenimiento y conciencia sobre el cuidado de la Ciudad.', 119),
	(96, 119, 'SSCYPE.1.9', 'Buenos Aires a bailar. EJE DE DISFRUTE  /CREATIVIDAD. Festival dirigido al publico joven con el fin de fomentar la diversion sana al aire libre bajo el eje del disfrute.', 119),
	(97, 41, 'MCGC.1.1', 'Revalorizar la identidad de las comunas y el trabajo colectivo', 41),
	(98, 42, 'MCGC.2.1', 'Consolidar el paradigma de la Ciudad Creativa', 42),
	(99, 43, 'MCGC.3.1', 'Ampliar la presencia cultural en la ciudad', 43),
	(100, 44, 'MCGC.4.1', 'Apoyar la difusión de nuevos artistas', 44),
	(101, 45, 'MCGC.5.1', 'Construir un nuevo diagrama de la escena cultural en Buenos Aires', 45),
	(102, 101, 'SECCCYFP.1.1', 'Brindar formación de calidad para los empleados de gobierno con el fin de lograr el desarrollo personal y organizacional deseado.', 101),
	(103, 101, 'SECCCYFP.1.2', 'Mejorar de forma continua la cultura organizacional y la calidad de servicio de los empleados con una comunicación interna efectiva.', 101),
	(104, 102, 'SECCCYFP.2.1', 'Generar y fomentar el sentido de pertenencia y compromiso ciudadano valorando el espacio público.', 102),
	(105, 102, 'SECCCYFP.2.2', 'Coordinar vínculos de relacionamiento estratégico con el sector empresario e instituciones del tercer sector.', 102),
	(106, 102, 'SECCCYFP.2.3', 'Generar cambios culturales que promuevan la convivencia y participación.', 102),
	(107, 124, 'SSDCCYC.1.1', 'Facilitar la interacción del vecino en terminos de su experiencia con las sedes en donde realiza sus trámites', 124),
	(108, 124, 'SSDCCYC.1.2', 'Incrementar el número de canales de ingreso de reclamos Mejorar las interfaces de comunicación de los vecinos con el GCBA con el fin de mejorar la experiencia del usuario', 124),
	(109, 124, 'SSDCCYC.1.3', 'Integrar los Sistemas de Atención Ciudadana', 124),
	(110, 124, 'SSDCCYC.1.4', 'Identificar tendencias y anticipar nuevos focos demandas', 124),
	(111, 124, 'SSDCCYC.1.5', 'Garantizar el correcto funcionamiento de la Gestion de Citas', 124),
	(112, 125, 'SSDCCYC.2.1', 'Estandarizar   procesos y calidad de Atención', 125),
	(113, 126, 'SSDCCYC.3.1', 'Brindar un sistema de información integrado con el fin de Incrementar el índice de resolución de reclamos relacionados al Mantenimiento del Espacio Público  durante 201Lograr el cumplimiento de los SLA establecidos y garantizar la respuesta del 100% de los reclamos ingresados', 126),
	(114, 126, 'SSDCCYC.3.2', 'Garantizar la verificación del 100% del universo de Reclamos', 126),
	(115, 127, 'SSDCCYC.4.1', 'Incrementar la periodicidad de las mediciones de calidad sobre áreas clave respecto del año anterior y evaluar correcciones sugeridas (antes semestral, ahora cuatrimestral)', 127),
	(116, 127, 'SSDCCYC.4.2', 'Colaborar en la definición de los SLA y los estándares básicos de calidad para los procesos clave de los servicios más demandados (Reclamos, registro civil, rentas, licencias, infracciones, salud, 147 y Web)', 127),
	(117, 127, 'SSDCCYC.4.3', 'Preparar la reformulación de los procesos con el objetivo de la futura certificación de normas y estándares internacionales', 127),
	(118, 128, 'SSDCCYC.5.1', 'Fortalecer la conciliación como respuesta ágil y eficiente ante los conflictos entre las empresas y consumidores en la comercialización de bienes y servicios Aumentando la proporción al 60%', 128),
	(119, 128, 'SSDCCYC.5.2', 'Empoderar al Consumidor a través de la Información incrementando el indice de conocimiento al 60%', 128),
	(120, 128, 'SSDCCYC.5.3', 'Detectar e iniciar de oficio actuaciones contra prácticas abusivas y publicidad engañosa en violación a la Legislación vigente', 128),
	(121, 103, 'SECDES.1.1', 'Mejorar los Espacios Publicos Comunales en respuesta a demandas vecinales.', 103),
	(122, 103, 'SECDES.1.2', 'Continuar y asistir en el proceso de Descentralización.', 103),
	(123, 103, 'SECDES.1.3', 'Fortalecer el vínculo con las autoridades comunales.', 103),
	(124, 104, 'SECDES.2.1', 'Cumplir con espacios óptimos para el desarrollo de los servicios y la mejora en la atención al ciudadano.', 104),
	(125, 104, 'SECDES.2.2', 'Reforzar el vínculo con organizaciones de acción comunitaria.', 104),
	(126, 104, 'SECDES.2.3', 'Difusión de la gestión', 104),
	(127, 104, 'SECDES.2.4', 'Fortalecer la identidad comunal.', 104),
	(128, 104, 'SECDES.2.5', 'Garantizar el acceso a Defensa al Consumidor en las Comunas.', 104),
	(129, 104, 'SECDES.2.6', 'Propiciar el vínculo entre la Policía y los vecinos a través de la Comuna.', 104),
	(130, 105, 'SECDES.3.1', 'Propiciar medidas de ahorro energético en las Sedes Comunales y posicionar a la Comuna como centro de información y auxilio por cortes eléctricos.', 105),
	(131, 105, 'SECDES.3.2', 'Implementar sistemas expresados en la Ley 4237 y la Ley 4428.', 105),
	(132, 106, 'SECDES.4.1', 'Dotar a las sedes de una nueva tecnología en comunicación (Telefonía IP)', 106),
	(133, 106, 'SECDES.4.2', 'Certificacion para la Gestion de Calidad en Atenciòn al Cliente en las Comunas.', 106),
	(134, 106, 'SECDES.4.3', 'Adaptar los puestos de atención en función de optimizar los tiempos de trámite en cada servicio.', 106),
	(135, 106, 'SECDES.4.4', 'Definir indicadores para medir y asegurar la óptima calidad en la gestión de las competencias.', 106),
	(136, 106, 'SECDES.4.5', 'Profesionalización de los Recursos Humanos.', 106),
	(137, 107, 'SECDES.5.1', 'Optimizar la gestión del Arbolado Público.', 107),
	(138, 107, 'SECDES.5.2', 'Prevención del Uso Indebido del Espacio Público.', 107),
	(139, 107, 'SECDES.5.3', 'Garantizar la higiene de la Ciudad', 107),
	(140, 107, 'SECDES.5.4', 'Agilización de la gestión de la competencia.', 107),
	(141, 107, 'SECDES.5.5', 'Puesta en valor del Espacio Público.', 107),
	(142, 107, 'SECDES.5.6', 'Certificaciones', 107),
	(143, 107, 'SECDES.5.7', 'Garantizar la concreción de los proyectos y obras en tiempo y forma.', 107),
	(144, 107, 'SECDES.5.8', 'Disminuir los tiempos de atención al ciudadano.', 107),
	(145, 46, 'MHYDHGC.1.1', 'Brindan atención integral a AM en situación de calle.', 46),
	(146, 46, 'MHYDHGC.1.2', 'Responder a las situaciones de emergencia en barrios vulnerables a través de brindar servicios.', 46),
	(147, 46, 'MHYDHGC.1.3', 'Fortalecer y empoderar a todas las mujeres víctimas de violencia para que puedan acceder a una vida libre de violencia.', 46),
	(148, 46, 'MHYDHGC.1.4', 'Generar todas las instancias necesarias para asistir a todos los ciudadanos de la Ciudad de Buenos Aires, que requieran atención y contención por el uso de sustancias psicoactivas, buscando garantizar la calidad y efectividad de cada intervención, con la reinserción social como eje transversal.', 46),
	(149, 46, 'MHYDHGC.1.5', 'Brindar asistencia social inmediata a las personas que se encuentren en situación de calle.', 46),
	(150, 46, 'MHYDHGC.1.6', 'Dar Refugio a Adultos Mayores víctimas de violencia.', 46),
	(151, 47, 'MHYDHGC.2.1', 'Aumentar el impacto de gestión a través de lograr una política pública más eficiente.', 47),
	(152, 48, 'MHYDHGC.3.1', 'Planificar, diseñar -y construir- las obras que garanticen la integración física y social a través de la Gestión Social del Hábitat, conjuntamente con un trabajo territorial de acompañamiento, empoderamiento y desarrollo de las capacidades de la comunidad por medio de la participación ciudadana en la transformación urbana y social de sus barrios.', 48),
	(153, 48, 'MHYDHGC.3.2', 'Realizar los relevamientos necesarios para diagnosticar los territorios y obtener información sobre el resultado de las intervenciones y así poder diseñar y corregir las estrategias de abordaje en los mismos.', 48),
	(154, 48, 'MHYDHGC.3.3', 'Desarrollar e implementar la RED NIDO en villas y asentamientos.', 48),
	(155, 48, 'MHYDHGC.3.4', 'Revalorizar el deporte como herramienta de inclusión social e instrumento de intervención pedagógica.', 48),
	(156, 48, 'MHYDHGC.3.5', 'Incrementar la participación comunitaria.', 48),
	(157, 48, 'MHYDHGC.3.6', 'Revalorizar y potenciar la Cultura Viva Comunitaria como motor de la transformación e inclusión social.', 48),
	(158, 48, 'MHYDHGC.3.7', 'Empoderar a las mujeres e incluir la perspectiva de género en el diseño y la implementación de los planes de urbanización.', 48),
	(159, 48, 'MHYDHGC.3.8', 'Promover el trabajo colaborativo entre la comunidad, organizaciones del tercer sector, organizaciones de base, el sector privado, el sector académico y el Estado.', 48),
	(160, 49, 'MHYDHGC.4.1', 'Garantizar el crecimiento y desarrollo saludable de los niños y niñas y adolescentes en situación de vulnerabilidad social de la Ciudad de Buenos Aires, en pos de favorecer la promoción y protección de sus derechos.', 49),
	(161, 49, 'MHYDHGC.4.2', 'Promover a través del juego el derecho a crecer en libertad, en conocimiento de los propios derechos.', 49),
	(162, 49, 'MHYDHGC.4.3', 'Promover los cuidados prenatales para un desarrollo saludable del embarazo y los primeros meses.', 49),
	(163, 49, 'MHYDHGC.4.4', 'Diseñar e implementar políticas que garanticen la asistencia integral, protección de derechos, inclusión social y el acceso a nuevas tecnologías de los adultos mayores.', 49),
	(164, 49, 'MHYDHGC.4.5', 'Mejorar la calidad de vida de las personas con discapacidad y sus familias.', 49),
	(165, 49, 'MHYDHGC.4.6', 'Brindar herramientas para personas con discapacidad que están estudiando.', 49),
	(166, 49, 'MHYDHGC.4.7', 'Aumentar los niveles de acceso al pleno goce de derechos de todos los ciudadanos en CABA.', 49),
	(167, 50, 'MDUYTGC.1.1', 'Buenos Aires Ciudad Verde - Plan Hidráulico', 50),
	(168, 59, 'MDUYTGC.10.1', 'Incrementar la participación de la inversión privada en los proyecytos urbanos', 59),
	(169, 51, 'MDUYTGC.2.1', 'Buenos Aires Ciudad Verde - Plan de Sustentabilidad Verde', 51),
	(170, 52, 'MDUYTGC.3.1', 'Trabajar sobre la extension y el servicio del subte para incrementar la cantidad de usuarios', 52),
	(171, 53, 'MDUYTGC.4.1', 'Mejorar el transporte público en superficie', 53),
	(172, 53, 'MDUYTGC.4.2', 'Seguridad Vial', 53),
	(173, 53, 'MDUYTGC.4.3', 'Promover el uso de la bicicleta como medio de transporte', 53),
	(174, 53, 'MDUYTGC.4.4', 'Ordenamiento del tránsito', 53),
	(175, 53, 'MDUYTGC.4.5', 'Mejora de la atención al ciudadano', 53),
	(176, 53, 'MDUYTGC.4.6', 'Impulsar la movilidad peatonal', 53),
	(177, 53, 'MDUYTGC.4.7', 'Aumentar la participación del transporte público y el no motorizado por sobre el uso del automóvil particular en la Ciudad y el Área Metropolitana', 53),
	(178, 53, 'MDUYTGC.4.8', 'Mejorar e incentivar la movilidad en ferrocarril', 53),
	(179, 53, 'MDUYTGC.4.9', 'Eliminar pasos ferroviarios a nivel', 53),
	(180, 53, 'MDUYTGC.4.10', 'Mejorar la movilidad frente a las barreras urbanas', 53),
	(181, 53, 'MDUYTGC.4.11', 'Mejorar la conectividad con los partidos colindantes a la Ciudad', 53),
	(182, 53, 'MDUYTGC.4.12', 'Movilidad Sustentable', 53),
	(183, 54, 'MDUYTGC.5.1', 'Plan de Integración Urbana', 54),
	(184, 54, 'MDUYTGC.5.2', 'Buenos Aires Ciudad Verde - Plan de Sustentabilidad Verde', 54),
	(185, 54, 'MDUYTGC.5.3', 'Buenos Aires Ciudad Verde - Plan de Sustentabilidad Verde', 54),
	(186, 54, 'MDUYTGC.5.4', 'Intervenciones para mejorar la infraestructura de obras vigentes', 54),
	(187, 55, 'MDUYTGC.6.1', 'Relocalización de edificios Gubernamentales', 55),
	(188, 55, 'MDUYTGC.6.2', 'Bienes culturales', 55),
	(189, 55, 'MDUYTGC.6.3', 'Planes entorno a las arterias de la Ciudad de Buenos Aires', 55),
	(190, 55, 'MDUYTGC.6.4', 'Contribución a los planes para la urbanización de las villas', 55),
	(191, 55, 'MDUYTGC.6.5', 'Estudio del impacto urbano de la Obra', 55),
	(192, 55, 'MDUYTGC.6.6', 'Plan Estratégico', 55),
	(193, 55, 'MDUYTGC.6.7', 'Programas de densificación', 55),
	(194, 55, 'MDUYTGC.6.8', 'Programa Código', 55),
	(195, 56, 'MDUYTGC.7.1', 'Barrio Parque Donado Holmberg - Vivienda', 56),
	(196, 56, 'MDUYTGC.7.2', 'Plan Urbano Integral Comuna Olímpica - Vivienda', 56),
	(197, 57, 'MDUYTGC.8.1', 'Barrio Parque Donado Holmberg - Infraestructura', 57),
	(198, 57, 'MDUYTGC.8.2', 'Plan Urbano Integral Comuna Olímpica - Infraestructura', 57),
	(199, 58, 'MDUYTGC.9.1', 'Mejorar el transporte público en superficie', 58),
	(200, 58, 'MDUYTGC.9.2', 'Movilidad Sustentable', 58),
	(201, 60, 'MEGC.1.1', 'Mejorar el aprendizaje de los alumnos la escuela primaria y secundaria.', 60),
	(202, 61, 'MEGC.2.1', 'Incremento de vacantes para sala de 3 años.', 61),
	(203, 61, 'MEGC.2.2', 'Reducir el abandono en la Escuela Media', 61),
	(204, 61, 'MEGC.2.3', 'Igualar las oportunidades de educación de la escuela primaria en toda la Ciudad de Buenos Aires reduciendo la brecha entre comunas de los resultados de las evaluaciones censales.', 61),
	(205, 62, 'MEGC.3.1', 'Aumentar la matrícula de estudiantes de los institutos de formación docente.', 62),
	(206, 62, 'MEGC.3.2', 'Consolidar un Sistema Integral de Información Educativa. (Abarca la escuela, el alumno y el docente)', 62),
	(207, 62, 'MEGC.3.3', 'Plus Escuelas', 62),
	(208, 62, 'MEGC.3.4', 'Optimizar las condiciones edilicias de las escuelas de la ciudad', 62),
	(209, 63, 'MEGC.4.1', 'Docentes capacitados en nuevas tecnologías.', 63),
	(210, 63, 'MEGC.4.2', 'Mejorar las habilidades socioemocionales.', 63),
	(211, 63, 'MEGC.4.3', 'Fomentar el emprendedurismo en las escuelas secundarias.', 63),
	(212, 63, 'MEGC.4.4', 'Escuelas primarias y medias con jornada extendida virtual.', 63),
	(213, 64, 'MEGC.5.1', 'Mejorar el posicionamiento del valor de la escuela y los educadores / Aumentar elíndice de confiabilidad de las instituciones educadoras.', 64),
	(214, 65, 'MGOBGC.1.1', 'Posicionar a la CABA como referente de gestión responsable, abierta e innovadora', 65),
	(215, 65, 'MGOBGC.1.2', 'Fortalecer las relaciones institucionales con el PL', 65),
	(216, 65, 'MGOBGC.1.3', 'Liderar los procesos de generación de consensos para la construcción de una identidad metropolitana', 65),
	(217, 65, 'MGOBGC.1.4', 'Coordinar en conjunto con la Sec. Gral la agenda y el trabajo del Gabinete Metropolitano', 65),
	(218, 66, 'MGOBGC.2.1', 'AMPLIACION DE LA CAPACIDAD DE DISPOSICION FINAL', 66),
	(219, 66, 'MGOBGC.2.2', 'INCREMENTO DE LA CAPACIDAD DE TRATAMIENTO DE RSU', 66),
	(220, 66, 'MGOBGC.2.3', 'AUMENTO CAPACIDAD DE GENERARACION DE ENERGIA LIMPIA', 66),
	(221, 67, 'MGOBGC.3.1', 'Dar impulso a los procesos de transferencias de competencias entre Nación y Ciudad', 67),
	(222, 67, 'MGOBGC.3.2', 'Potenciar la presencia y acción de la CABA en los Organismos Interjurisdiccionales', 67),
	(223, 68, 'MHGC.1.1', 'Desarrollar e implementar alternativas de ahorros en gastos corrientes y de mantenimiento.', 68),
	(224, 68, 'MHGC.1.2', 'Desarrollar e implementar proyectos para la generación y la optimización del cobro de otros ingresos.', 68),
	(225, 69, 'MHGC.2.1', 'Satisfacer las necesidades de financiamiento del Tesoro mediante instrumentos de corto y largo plazo, manteniendo la reputación de la Ciudad en los mercados como agente de crédito mediante la buena gestión y una continua comunicación con los actores relevantes.', 69),
	(226, 69, 'MHGC.2.2', 'Desarrollar alternativas de financiamiento con Organismos Multilaterales de Crédito o Agencias de Cooperación Técnica y/o Financiera.', 69),
	(227, 70, 'MHGC.3.1', 'Incorporar mejoras a procesos transversales del Ministerio donde se brinde soporte a clientes internos.', 70),
	(228, 70, 'MHGC.3.2', 'Optimizar los procesos en los que el Ministerio interactúe con ciudadanos.', 70),
	(229, 70, 'MHGC.3.3', 'Mejorar los procesos donde tiene lugar la interrelación con proveedores.', 70),
	(230, 71, 'MHGC.4.1', 'Planificar el capital humano.', 71),
	(231, 71, 'MHGC.4.2', 'Profesionalizar y desarrollar al servidor público.', 71),
	(232, 71, 'MHGC.4.3', 'Implementar estrategias e incentivos que promuevan la productividad.', 71),
	(233, 72, 'MHGC.5.1', 'Sistematizar el seguimiento y la evolución de los factores que explican el comportamiento de la recaudación tributaria.', 72),
	(234, 72, 'MHGC.5.2', 'Monitorear la estimación de las principales variables macroeconómicas mediante la interacción con equipos técnicos de otros organismos y jurisdicciones.', 72),
	(235, 72, 'MHGC.5.3', 'Conformar un equipo de seguimiento que realice monitoreos periódicos detectando potenciales desvíos sobre gastos presupuestados.', 72),
	(236, 73, 'MHGC.6.1', 'Aumentar la recaudación, la percepción del riesgo y fortalecer la inteligencia fiscal.', 73),
	(237, 73, 'MHGC.6.2', 'Fortalecer el control de gestión con el fin de gerenciar la operación y tomar decisiones en base a información objetiva, confiable y oportuna.', 73),
	(238, 73, 'MHGC.6.3', 'Mejorar la atención al contribuyente fomentando la cultura tributaria y el cumplimiento voluntario de las obligaciones fiscales.', 73),
	(239, 73, 'MHGC.6.4', 'Optimizar el modelo de operación de la Administración buscando maximizar el aprovechamiento de los recursos humanos, tecnológicos y financieros.', 73),
	(240, 32, 'SECISYU.1.1', 'Mejorar el acceso a servicios básicos e infraestructura sanitaria', 32),
	(241, 32, 'SECISYU.1.2', 'Mejorar la conectividad y generar espacio público', 32),
	(242, 33, 'SECISYU.2.1', 'Promover la empleabilidad, formalidad y sostenibilidad económica de la población económicamente activa en el territorio', 33),
	(243, 34, 'SECISYU.3.1', 'Brindar soluciones habitacionales dignas a las familias relocalizadas de manera involuntaria y/o residentes en el "Bajo Autopista"', 34),
	(244, 34, 'SECISYU.3.2', 'Garantizar viviendas cuyos materiales y técnicas de construcción adecuadas, donde se suministren espacios y seguros para vivir, integrados a una estructura comunitaria', 34),
	(245, 35, 'SECISYU.4.1', 'Mejorar la calidad y el acceso a los servicios públicos y sociales brindados a la ciudadanía', 35),
	(246, 74, 'MMIYTGC.1.1', 'Implementar las mejores prácticas de sustentabildad en el Gobierno de la Ciudad de Buenos Aires.', 74),
	(247, 74, 'MMIYTGC.1.2', 'Generar soluciones a la problemática de los residuos sólidos urbanos a través de la creacion de Estaciones Automáticas de Reciclado.', 74),
	(248, 74, 'MMIYTGC.1.3', 'Promover la sustentabilidad en base a desarrollo de productos innovadores.', 74),
	(249, 83, 'MMIYTGC.10.1', 'Trabajar con los diferentes actores, en el mejoramiento de las cadenas productivas de valor', 83),
	(250, 83, 'MMIYTGC.10.2', 'Desarrollar herramientas y espacios formales de inclusión y desarrollo productivo / emprendedor', 83),
	(251, 83, 'MMIYTGC.10.3', 'Orientar las programas de formación, financiamiento y articulación en función de las oportunidades de mercado y la realidad local de las poblaciones más vulnerable de la ciudad.', 83),
	(252, 83, 'MMIYTGC.10.4', 'Fomentar la innovación en productos y servicios en empresas y de emprendedores para mejorar la calidad de vida de la población más vulnerable', 83),
	(253, 84, 'MMIYTGC.11.1', 'Fortalecer la comunicación en medios tradicionales y digitales', 84),
	(254, 84, 'MMIYTGC.11.2', 'Celebrar eventos de difusión e iniciación deportiva', 84),
	(255, 84, 'MMIYTGC.11.3', 'Continuar y fortalecer las actividades en las escuelas', 84),
	(256, 85, 'MMIYTGC.12.1', 'Difundir y dar a conocer el proyecto Buenos Aires 2018', 85),
	(257, 85, 'MMIYTGC.12.2', 'Relevamiento y definición de la infraestructura tecnológica necesaria', 85),
	(258, 85, 'MMIYTGC.12.2', 'Relevamiento y definición de la infraestructura tecnológica necesaria', 85),
	(259, 85, 'MMIYTGC.12.3', 'Desarrollo de la versión final del Venue Master Plan', 85),
	(260, 86, 'MMIYTGC.13.1', 'Elaborar una política de desarrollo de nuevos distritos y áreas comerciales', 86),
	(261, 86, 'MMIYTGC.13.2', 'Hacer de Bs As un destino relevante para la comunidad inversora internacional', 86),
	(262, 86, 'MMIYTGC.13.3', 'Buscar firmas extranjeras que quieran basar sus operaciones globales en CABA Proveer un único punto de contacto de gestión, venta y aftercare', 86),
	(263, 86, 'MMIYTGC.13.4', 'Proveer un único punto de contacto de gestión, venta y aftercare', 86),
	(264, 87, 'MMIYTGC.14.1', 'Reglamentación y/o reforma de la ley de Iniciativas Público-Privadas', 87),
	(265, 87, 'MMIYTGC.14.2', 'Financiamiento a Pymes y Start-Ups vía organismos multilaterales y Venture Capital', 87),
	(266, 87, 'MMIYTGC.14.3', 'Utilizar “Crowdfunding” como instrumento  de Iniciativa Público-Privada', 87),
	(267, 88, 'MMIYTGC.15.1', 'Ser un estado presente en el dialogo para la prevención de conflictos', 88),
	(268, 89, 'MMIYTGC.16.1', 'Potenciar a las Pymes en desarrollo sustentable', 89),
	(269, 89, 'MMIYTGC.16.2', 'Fomentar la empleabilidad desde la articulación', 89),
	(270, 90, 'MMIYTGC.17.1', 'Generación de proyectos en conjunto con otras áreas de GCBA y Nación', 90),
	(271, 91, 'MMIYTGC.18.1', 'Generar Bases de Datos Inteligente.', 91),
	(272, 91, 'MMIYTGC.18.2', 'Potenciar el uso y la comunicación de la Plataforma Digital (Web y Móvil).', 91),
	(273, 91, 'MMIYTGC.18.3', 'Promover la transparencia y la apertura de la información.', 91),
	(274, 92, 'MMIYTGC.19.1', 'Desarrollar el plan de Marca Ciudad', 92),
	(275, 92, 'MMIYTGC.19.2', 'Ser sede de Eventos de Alta Visibilidad Mundial', 92),
	(276, 75, 'MMIYTGC.2.1', 'Desarrollar mercados y segmentos junto con el sector privado', 75),
	(277, 75, 'MMIYTGC.2.2', 'Impulsar experiencias turísticas únicas en Buenos Aires para cada segmento y promocionarlas', 75),
	(278, 75, 'MMIYTGC.2.3', 'Potenciar el posicionamiento del Centro de Convenciones de Buenos Aires', 75),
	(279, 93, 'MMIYTGC.20.1', 'Ampliar la Infraestructura Inteligente', 93),
	(280, 93, 'MMIYTGC.20.2', 'Promover la inclusión social a través de la tecnología.', 93),
	(281, 94, 'MMIYTGC.21.1', 'Generar información para la toma de decisiones, identificar tendencias, orientar y medir las acciones del plan', 94),
	(282, 95, 'MMIYTGC.22.1', 'Potenciar la calidad de la oferta turística', 95),
	(283, 95, 'MMIYTGC.22.2', 'Desarrollar nuevas experiencias turísticas alineadas a los objetivos de Ciudad Verde e Inteligente de CABA', 95),
	(284, 76, 'MMIYTGC.3.1', 'Crear espacios físicos y virtuales de co-creación y participación.', 76),
	(285, 76, 'MMIYTGC.3.2', 'Fomentar procesos y programas de innovación para la solucionar las problemáticas de la Ciudad.', 76),
	(286, 76, 'MMIYTGC.3.3', 'Prototipar soluciones disruptivas escalables.', 76),
	(287, 77, 'MMIYTGC.4.1', 'Fortalecer la comunicación con organismos internacionales', 77),
	(288, 77, 'MMIYTGC.4.2', 'Defnición y estimación de recursos', 77),
	(289, 77, 'MMIYTGC.4.3', 'Desarrollo de planes operativos y estratégicos', 77),
	(290, 77, 'MMIYTGC.4.4', 'Fortalecer la comunicación dentro de la comunidad local', 77),
	(291, 78, 'MMIYTGC.5.1', 'Generar e institucionalizar redes de conocimiento, colaboración y generación de valor compartido en el Ecosistema', 78),
	(292, 78, 'MMIYTGC.5.2', 'Incorporar herramientas científicas y tecnologicas para innovar dentro y fuera del del sistema público .', 78),
	(293, 78, 'MMIYTGC.5.3', 'Fortalecer la transferencia tecnológica orientada a resultados y  aumentar la cantidad de patentes de ciudad', 78),
	(294, 78, 'MMIYTGC.5.4', 'Facilitar el acceso a la información, financiamiento y capacitación de los diferentes sectores', 78),
	(295, 79, 'MMIYTGC.6.1', 'Estimular el espiritu y la actitud creativa, innovadora y emprendedora', 79),
	(296, 79, 'MMIYTGC.6.2', 'Identificar, conectar y potenciar el talento local con el país y el mundo', 79),
	(297, 80, 'MMIYTGC.7.1', 'Promover al emprendimiento (de base tecnológica, cultura y social) y el trabajo productivo como motor de desarrollo económico', 80),
	(298, 80, 'MMIYTGC.7.2', 'Fortalecer el ecosistema emprendedor local', 80),
	(299, 80, 'MMIYTGC.7.3', 'Detectar y acompañar a emprendedores de alto impacto', 80),
	(300, 81, 'MMIYTGC.8.1', 'Generar espacios de intecambio y promoción de las industrias a nivel local', 81),
	(301, 81, 'MMIYTGC.8.2', 'Incorporar capacidades tecnológicas, creativas y de comercio exterior en emprendedores, Pymes, Empresas y otras instituciones y aumentar su competitividad', 81),
	(302, 81, 'MMIYTGC.8.3', 'Potenciar y afianzar el desarrollo de las industrias creativas en la ciudad, de la mano de los Distritos', 81),
	(303, 81, 'MMIYTGC.8.4', 'Promover y aumentar la internalización de las industrias estratégicas', 81),
	(304, 82, 'MMIYTGC.9.1', 'Detectar y generar los marcos legales adecuados, que permitan integrar a la vida de la ciudad, las nuevas tendencias de generación económica', 82),
	(305, 82, 'MMIYTGC.9.2', 'Generar incentivos públicos que potencien el desarrollo de las tendencias de desarrollo económico local', 82),
	(306, 120, 'SSSYP.1.1', 'Desarrollar, en las distintas áreas del GCBA, proyectos relacionados con el relevamiento, diseño e implementación de procesos verticales y transversales de gestión, aplicando las herramientas de soporte documental y transaccional disponibles, promoviendo la integración de ambas y asistir a las áreas de gobierno en la definición e implementación de modelos informáticos de explotación de datos', 120),
	(307, 120, 'SSSYP.1.2', 'Profundizar la transformación evolutiva del gobierno electrónico, priorizando las necesidades de los ciudadanos', 120),
	(308, 121, 'SSSYP.2.1', 'Evolucionar en términos de visión tecnológica (Por área interna)', 121),
	(309, 121, 'SSSYP.2.2', 'Nuevo servicio al ciudadano y GCBA', 121),
	(310, 121, 'SSSYP.2.3', 'Asegurar la Continuidad Operacional (Por área interna)', 121),
	(311, 122, 'SSSYP.3.1', 'Asegurar la Continuidad Operacional (Por área interna)', 122),
	(312, 123, 'SSSYP.4.1', 'Estructurar la Organización orientada a Servicios con capacidad para el cumplimiento de SLAs', 123),
	(313, 96, 'MSGC.1.1', 'Ley de Historia Clínica Única Electrónica', 96),
	(314, 96, 'MSGC.1.2', 'Historia clínica electrónica única', 96),
	(315, 96, 'MSGC.1.3', 'Estrategia de turnos y sistemas gubernamentales de salud', 96),
	(316, 96, 'MSGC.1.4', 'Salud Móvil y Comunicación Ciudadana', 96),
	(317, 97, 'MSGC.2.1', 'Plan Integral de Seguridad', 97),
	(318, 97, 'MSGC.2.2', 'Coordinación con la Agencia Gubernamental de Control', 97),
	(319, 97, 'MSGC.2.3', 'Mejora del sistema de atención en el AMBA', 97),
	(320, 97, 'MSGC.2.4', 'Delegación de compentencias de regulación y fiscalización', 97),
	(321, 97, 'MSGC.2.5', 'Acuerdos interministeriales', 97),
	(322, 98, 'MSGC.3.1', 'Fortalecer la red de APS', 98),
	(323, 98, 'MSGC.3.2', 'Potenciar la estrategia de salud comunitaria', 98),
	(324, 98, 'MSGC.3.3', 'Crear servicios ambulatorios extrahospitalarios en red', 98),
	(325, 99, 'MSGC.4.1', 'Diseño Plan Integral 2030', 99),
	(326, 99, 'MSGC.4.2', 'Finalización de Obras Críticas', 99),
	(327, 99, 'MSGC.4.3', 'Crear Master Plan de Obras 2030', 99),
	(328, 99, 'MSGC.4.4', 'Rediseñar los sistemas de guardias, traslados y SAME', 99),
	(329, 100, 'MSGC.5.1', 'Incrementar la eficiencia del gasto', 100),
	(330, 100, 'MSGC.5.2', 'Aumentar el ingreso de terceros financiadores', 100),
	(331, 108, 'SGYRI.1.1', 'Integrar Buenos Aires al mundo/Buenos Aires Comunidad de Inversión, Ideas y Talentos.', 108),
	(332, 108, 'SGYRI.1.2', 'Visibilizar la Gestión y la Ciudad en el Mundo', 108),
	(333, 108, 'SGYRI.1.3', 'Promover espacios de encuentro interreligioso y acompañar las distintas iniciativas que realizan las comunidades de Fe y organizaciones de dialogo interreligioso', 108),
	(334, 109, 'SGYRI.2.1', 'Liderar la planificación y coordinación de los Festejos del Bicentenario de la Independencia', 109),
	(335, 109, 'SGYRI.2.2', 'Crear una identidad disruptiva de la forma de relacionarse del Gobierno, fomentando la cercanía y calidez del Jefe de Gobierno', 109),
	(336, 109, 'SGYRI.2.3', 'Revitalizar al CoPE como espacio de relacionamiento del Gobierno con OSCs', 109),
	(337, 109, 'SGYRI.2.4', 'Acompañar los proyectos de promoción social e inclusión que llevan a cabo las comunidades de fe de la CABA', 109),
	(338, 110, 'SGYRI.3.1', 'Contribuir al cumplimiento de los objetivos y compromisos prioritarios de gobierno', 110),
	(339, 110, 'SGYRI.3.2', 'Potenciar la implementación de políticas transversales e interjurisdiccionales', 110),
	(340, 110, 'SGYRI.3.3', 'Internacionalizar la gestión de la Ciudad', 110),
	(341, 111, 'SGCBA.1.1', 'Diseñar la Matriz de Riesgo por tipología derivada de los enunciados de la Ley N° 70/98 (Presupuestarios, Económicos, Financieros, patrimoniales, Normativos y de Gestión)', 111),
	(342, 111, 'SGCBA.1.2', 'Implementar una herramienta de autodiagnóstico y mejora para la Madurez del Control Interno', 111),
	(343, 111, 'SGCBA.1.3', 'Promover la certificación de la norma ISO 9001:2015 en todas las Unidades de Auditoría Interna y las principales ocho (8) Dependencias de Atención al Ciudadano', 111),
	(344, 111, 'SGCBA.1.4', 'Actualizar y Editar el Nuevo Manual de Control Interno del Gobierno de la Ciudad de Buenos Aires (Versión impresa y digital)', 111),
	(345, 111, 'SGCBA.1.5', 'Relacionar a la Sindicatura con los Organismos Nacionales e Internacionales de Control Interno que profundicen nuestro conocimiento profesional y académico', 111),
	(346, 111, 'SGCBA.1.6', 'Promover la cultura de la excelencia académica en el control interno, a través de actividades de formación, intercambio de experiencias y trabajo coordinado', 111),
	(347, 112, 'SGCBA.2.1', 'Ser el órgano de consulta, capacitación e implementación del Control Interno a partir del Diálogo Interministerial', 112),
	(348, 112, 'SGCBA.2.2', 'Fortalecer la relación institucional entre la Sindicatura y las distintas áreas de gobierno', 112),
	(349, 113, 'SGCBA.3.1', 'Garantizar a la Sindicatura un nivel de Control Interno con los estándares internacionales de Madurez y Calidad Institucional', 113),
	(350, 113, 'SGCBA.3.2', 'Revisar y diagnosticar los recursos humanos de la Sindicatura (Idoneidad profesional, Retiros, Jubilaciones, Comisiones, Licencias, etc.)', 113),
	(351, 113, 'SGCBA.3.3', 'Lograr un nivel de capacitación de excelencia de los Agentes Gubernamentales comprometidos con el Control Interno', 113),
	(352, 113, 'SGCBA.3.4', 'Elaborar los informes de Auditoría y las Minutas para el Jefe de Gobierno. Llevar a cabo Auditorías por programas presupuestarios', 113),
	(353, 113, 'SGCBA.3.5', 'Mejorar el Sistema Informático de Control Interno', 113),
	(354, 129, 'AVJG.1.1', 'Generar proyectos legislativos relacionados a Juventud', 129),
	(355, 129, 'AVJG.1.2', 'Generar convenios para realizar acciones conjuntas con otras áreas para fortalecer los ejes de trabajo', 129),
	(356, 138, 'AVJG.10.1', 'Promoción mediante eventos gastronómicos especiales', 138),
	(357, 138, 'AVJG.10.2', 'Promoción masiva', 138),
	(358, 138, 'AVJG.10.3', 'Consolidar la temática mediante la institución de museos referentes', 138),
	(359, 139, 'AVJG.11.1', 'Fomentar la orientación familiar', 139),
	(360, 139, 'AVJG.11.2', 'Evaluación del impacto familiar', 139),
	(361, 140, 'AVJG.12.1', 'Promoción Masiva', 140),
	(362, 140, 'AVJG.12.2', 'Eventos Especiales', 140),
	(363, 130, 'AVJG.2.1', 'Reducir las enfermedades de transmisión sexual entre los jóvenes y los embarazos prematuros', 130),
	(364, 130, 'AVJG.2.2', 'Impulsar las conductas saludables entre los jóvenes', 130),
	(365, 131, 'AVJG.3.1', 'Generar una fuente de información estadística confiable sobre las situaciones que afectan la juventud y de seguimiento de políticas públicas aplicadas a los jóvenes', 131),
	(366, 132, 'AVJG.4.1', 'Incentivar la expresión cultural joven', 132),
	(367, 132, 'AVJG.4.2', 'Disponer de espacios de comunicación e información joven', 132),
	(368, 132, 'AVJG.4.3', 'Disponer de espacios de encuentro e integración para jóvenes', 132),
	(369, 132, 'AVJG.4.4', 'Fomentar el compromiso ciudadano y democrático de los jóvenes de la Ciudad', 132),
	(370, 133, 'AVJG.5.1', 'Reducir el desempleo joven', 133),
	(371, 133, 'AVJG.5.2', 'Posicionar a la Ciudad de Buenos Aires como el principal polo universitario de Latinoamérica', 133),
	(372, 134, 'AVJG.6.1', 'Potenciar el Desarrollo Deportivo, Igualando las oportunidades de acceso a la actividad deportiva', 134),
	(373, 134, 'AVJG.6.2', 'Posicionar a BA como la Ciudad Activa referente de Latinoamerica', 134),
	(374, 134, 'AVJG.6.3', 'Fortalecer y promover los clubes barriales', 134),
	(375, 134, 'AVJG.6.4', 'Generar y potenciar la cercanía del deporte y actividad física al Ciudadano identificando y potenciando espacios urbanos', 134),
	(376, 134, 'AVJG.6.5', 'Promover el espíritu y los valores olímpicos de la Ciudad', 134),
	(377, 134, 'AVJG.6.6', 'Infraestructura. Mejorar y poner en valor las instalaciones deportivas (12 polideportivos existentes y 4 parques)', 134),
	(378, 135, 'AVJG.7.1', 'Desarrollar políticas y acciones que pongan en relieve el valor y la riqueza del pluralismo cultural.', 135),
	(379, 135, 'AVJG.7.2', 'Difundir y promover la cultura cívica en Derechos Humanos, fomentando alianzas estratégicas con organismos e instituciones nacionales e internacionales.', 135),
	(380, 135, 'AVJG.7.3', 'Fomentar la convivencia urbana sostenida en el diálogo, la inclusión, el encuentro.', 135),
	(381, 135, 'AVJG.7.4', 'Posicionar a la CABA como modelo de integración y respeto por la diversidad sexual', 135),
	(382, 135, 'AVJG.7.5', 'Fomentar la integración de los colectivos inmigrantes en la Ciudad de Buenos Aires', 135),
	(383, 135, 'AVJG.7.6', 'Posicionar al Parque de la Memoria como el espacio público de referencia para la construcción de la memoria del terrorismo de estado, a través del arte y la educación.', 135),
	(384, 136, 'AVJG.8.1', 'Fomentar la gastronomía como política generadora de empleo, seguridad e inclusión social', 136),
	(385, 136, 'AVJG.8.2', 'Desarrollo de mercados de productos y platos elaborados', 136),
	(386, 137, 'AVJG.9.1', 'Atención de la población en Estaciones Saludables', 137),
	(387, 137, 'AVJG.9.2', 'Focalización en la problemática de sobrepeso infantil mediante el programa Mi Escuela Saludable', 137),
	(388, 137, 'AVJG.9.3', 'Investigación', 137),
	(389, 137, 'AVJG.9.4', 'Promoción de los programas saludables', 137),
	(390, 137, 'AVJG.9.5', 'Campaña masiva de concientización', 137),
	(391, 137, 'AVJG.9.6', 'Activaciones especiales', 137);
/*!40000 ALTER TABLE `objetivo_operativo` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.proyecto
CREATE TABLE IF NOT EXISTS `proyecto` (
  `idProyecto` int(10) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(50) DEFAULT NULL,
  `nombre` varchar(512) NOT NULL,
  `descripcion` text,
  `idObjetivoOperativo` int(11) DEFAULT NULL,
  `tipoProyecto` varchar(50) DEFAULT NULL,
  `meta` decimal(10,2) DEFAULT NULL,
  `unidadMeta` varchar(50) DEFAULT NULL,
  `poblacionAfectada` bigint(20) DEFAULT NULL,
  `liderProyecto` varchar(512) DEFAULT NULL,
  `area` varchar(512) DEFAULT NULL,
  `tipoUbicacionGeografica` varchar(50) DEFAULT NULL,
  `direccion` varchar(512) DEFAULT NULL,
  `cambioLegislativo` tinyint(4) DEFAULT NULL,
  `fechaInicio` date NOT NULL,
  `fechaFin` date DEFAULT NULL,
  `prioridadJurisdiccional` varchar(50) DEFAULT NULL,
  `estado` varchar(50) DEFAULT NULL,
  `idObjetivoOperativo2` int(11) NOT NULL,
  `idJurisdiccion2` int(11) DEFAULT NULL,
  `idObjetivoJurisdiccional2` int(11) DEFAULT NULL,
  `organismosCorresponsables` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`idProyecto`),
  KEY `FK_idObjetivoOperativo_proyecto` (`idObjetivoOperativo`),
  CONSTRAINT `FK_idObjetivoOperativo_proyecto` FOREIGN KEY (`idObjetivoOperativo`) REFERENCES `objetivo_operativo` (`idObjetivoOperativo`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.proyecto: ~24 rows (approximately)
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
INSERT INTO `proyecto` (`idProyecto`, `codigo`, `nombre`, `descripcion`, `idObjetivoOperativo`, `tipoProyecto`, `meta`, `unidadMeta`, `poblacionAfectada`, `liderProyecto`, `area`, `tipoUbicacionGeografica`, `direccion`, `cambioLegislativo`, `fechaInicio`, `fechaFin`, `prioridadJurisdiccional`, `estado`, `idObjetivoOperativo2`, `idJurisdiccion2`, `idObjetivoJurisdiccional2`, `organismosCorresponsables`) VALUES
	(1, 'test', 'test', 'test', 22, 'ampliacion', 10.20, 't', 10, NULL, NULL, NULL, NULL, 0, '2016-07-27', '2017-07-27', '3. Baja', 'TEST', 22, 1, 21, ''),
	(70, 'test', 'test', 'test', 57, 'test', 10.00, 't', 10, 'lider test', 'area test', 'test', 'direccion test', 1, '2016-07-27', '2016-07-27', 'test', 'TEST', 57, 50, 24, 'orgs'),
	(85, 'test', 'test2', 'test', 22, 'nuevo', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 1, '2016-07-27', '2016-07-27', '1. Alta', 'completo', 22, 50, 24, 'ninguno'),
	(86, 'test', 'test3', 'test', 22, 'nuevo', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 1, '2016-07-27', '2016-07-27', '1. Alta', 'completo', 22, 50, 24, 'ninguno'),
	(87, 'test', 'test4', 'test', 57, 'nuevo', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 1, '2016-07-27', '2016-07-27', '1. Alta', 'completo', 57, 50, 24, 'ninguno'),
	(88, 'test', 'test4', 'test', 57, 'nuevo', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 1, '2016-07-27', '2016-07-27', '1. Alta', 'completo', 57, 50, 24, 'ninguno'),
	(89, 'test', 'nuevo 1', 'test', 57, 'nuevo', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 1, '2016-07-27', '2016-07-27', '1. Alta', 'completo', 57, 50, 24, 'ninguno'),
	(90, 'test', 'test', 'test', 57, 'test', 10.00, 't', 10, 'lider test', 'area test', 'test', 'direccion test', 1, '2017-01-25', '2018-01-25', 'test', 'TEST', 57, 50, 24, 'orgs'),
	(91, 'test', 'test', 'test', 57, 'test', 10.00, 't', 10, 'lider test', 'area test', 'test', 'direccion test', 1, '2017-01-25', '2018-01-25', 'test', 'TEST', 57, NULL, 24, 'orgs'),
	(92, 'test', 'test5', 'test', 22, 'nuevo', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 0, '2016-07-27', '2016-07-27', '1. Alta', 'completo', 22, NULL, NULL, 'ninguno'),
	(93, NULL, 'Prueba desde cero 3', 'Esto es un proyecto nuevo', 22, 'nuevo', 20.00, 'Personas feas', 20000, 'Enoc Montiel', 'Clay', 'Comuna', '', 1, '2016-01-01', '2017-01-01', '2. Media', 'completo', 22, NULL, NULL, NULL),
	(94, 'test', 'prueba para fechas update', 'test', 22, 'ampliacion', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 0, '2016-07-27', '2016-07-27', '3. Baja', 'completo', 22, NULL, NULL, 'ninguno'),
	(105, 'test', 'Verificacion con fede de fechas', 'test', 22, 'ampliacion', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 1, '2016-07-27', '2020-07-27', '3. Baja', 'completo', 22, NULL, NULL, 'ninguno'),
	(108, 'test', 'prueba 2 con fede', 'test', 22, 'nuevo', 10.00, 't', 10, 'lider test', 'area test', 'Comuna', 'direccion test', 1, '2016-07-27', '2016-07-27', '3. Baja', 'completo', 22, NULL, NULL, 'ninguno'),
	(111, 'test', 'test presu 1', 'test', 57, 'test', 10.00, 't', 10, 'lider test', 'area test', 'test', 'direccion test', 1, '2017-01-25', '2018-01-25', 'test', 'TEST', 57, 50, 24, 'orgs'),
	(115, NULL, 'proyecto 1', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(116, NULL, 'proyecto 1', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(117, NULL, 'proyecto 3', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(118, NULL, 'proyecto 3', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(119, 'test', 'test presu 1', 'test', 57, 'test', 10.00, 't', 10, 'lider test', 'area test', 'test', 'direccion test', 1, '2017-01-25', '2018-01-25', 'test', 'TEST', 57, 50, 24, 'orgs'),
	(120, NULL, 'proyecto 5', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(121, NULL, 'proyecto 5', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(122, NULL, 'proyecto 7', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(123, NULL, 'proyecto 7', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(124, NULL, 'proyecto 9', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(125, NULL, 'proyecto 9', '', 22, '', 0.00, '', NULL, '', '', '', '', 0, '2017-12-12', NULL, NULL, 'Borrador', 22, 1, 21, ''),
	(126, 'test', 'test presu 2', 'test', 57, 'test', 10.00, 't', 10, 'lider test', 'area test', 'test', 'direccion test', 1, '2017-01-25', '2018-01-25', 'test', 'TEST', 57, 50, 24, 'orgs'),
	(127, 'test', 'cambiado', 'test', 57, 'test', 10.00, 't', 10, 'lider test', 'area test', 'test', 'direccion test', 1, '2016-07-27', '2016-07-27', 'test', 'TEST', 57, 50, 24, 'orgs');
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.comuna
CREATE TABLE IF NOT EXISTS `comuna` (
  `idComuna` int(11) NOT NULL AUTO_INCREMENT,
  `abreviatura` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idComuna`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.comuna: ~15 rows (approximately)
/*!40000 ALTER TABLE `comuna` DISABLE KEYS */;
INSERT INTO `comuna` (`idComuna`, `abreviatura`, `nombre`) VALUES
	(3, 'C1', 'Comuna 1'),
	(4, 'C2', 'Comuna 2'),
	(5, 'C3', 'Comuna 3'),
	(6, 'C4', 'Comuna 4'),
	(7, 'C5', 'Comuna 5'),
	(8, 'C6', 'Comuna 6'),
	(9, 'C7', 'Comuna 7'),
	(10, 'C8', 'Comuna 8'),
	(11, 'C9', 'Comuna 9'),
	(12, 'C10', 'Comuna 10'),
	(13, 'C11', 'Comuna 11'),
	(14, 'C12', 'Comuna 12'),
	(15, 'C13', 'Comuna 13'),
	(16, 'C14', 'Comuna 14'),
	(17, 'C15', 'Comuna 15');
/*!40000 ALTER TABLE `comuna` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.comuna_por_proyecto
CREATE TABLE IF NOT EXISTS `comuna_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idComuna` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idComuna`),
  KEY `FK_idUbicacionGeografica` (`idComuna`),
  CONSTRAINT `FK_idComuna` FOREIGN KEY (`idComuna`) REFERENCES `comuna` (`idComuna`),
  CONSTRAINT `FK_idProyecto_ubicacionGeografica` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.comuna_por_proyecto: ~20 rows (approximately)
/*!40000 ALTER TABLE `comuna_por_proyecto` DISABLE KEYS */;
INSERT INTO `comuna_por_proyecto` (`idProyecto`, `idComuna`) VALUES
	(1, 3),
	(70, 3),
	(85, 6),
	(86, 6),
	(87, 6),
	(88, 6),
	(89, 6),
	(90, 3),
	(91, 3),
	(92, 6),
	(93, 13),
	(93, 14),
	(93, 15),
	(94, 7),
	(105, 4),
	(105, 5),
	(105, 6),
	(108, 6),
	(111, 3),
	(119, 3),
	(126, 3),
	(127, 3);
/*!40000 ALTER TABLE `comuna_por_proyecto` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.eje_de_gobierno
CREATE TABLE IF NOT EXISTS `eje_de_gobierno` (
  `idEjeDeGobierno` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `ejemplos` varchar(512) NOT NULL,
  PRIMARY KEY (`idEjeDeGobierno`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.eje_de_gobierno: ~5 rows (approximately)
/*!40000 ALTER TABLE `eje_de_gobierno` DISABLE KEYS */;
INSERT INTO `eje_de_gobierno` (`idEjeDeGobierno`, `nombre`, `descripcion`, `ejemplos`) VALUES
	(3, 'Disfrute', 'Tenemos una Ciudad hermosa con infinitas posibilidades para disfrutarla cada día más. Queremos convivir mejor en esta Ciudad: que\nlos vecinos se sientan movilizados y comprometidos, que la cuiden y respeten participando activamente a través de la cultura, el deporte y el turismo. Y lograr la mejor Ciudad para todos.', 'Seguridad\nHigiene y Reciclado\nCambio Cultural\nParticipación Ciudadana\nTurismo\nPlan Gastronómico\nJJOO de la Juventud\nCultura cerca de la gente\nEcoparque'),
	(4, 'Escala Humana', 'Queremos que la gente camine por la calle y se encuentre. El espacio público tiene que disfrutarse, no es solo un lugar para transitar.\nEl espacio público es un servicio público.\nEs socializador, igualador, y brinda las mismas\noportunidades para todos.', 'Mantenimiento\nRegeneración Urbana\nTransporte Público\nPaseo del Bajo\nSolicitudes y Trámites'),
	(5, 'Integración social', 'Queremos una Ciudad al alcance de todos, donde todos los vecinos se sientan parte. Por eso, es nuestra prioridad seguir trabajando en una reforma integral del sistema de salud, en la educación y en el acompañamiento a nuestros adultos mayores.', 'Desarrollo del Sur\nIntegración de Villas (foco en 31 y 20)\nMejoras en Educación\nReforma Integral de Salud\nAdultos Mayores'),
	(6, 'Creatividad', 'Queremos una Ciudad donde la creatividad, el diseño y el arte sean pilares fundamentales de\nnuestra cultura. Una Ciudad inteligente, que facilite la comunicación, que apoye los proyectos emprendedores y el espíritu de trabajo y de mejora continua. Por eso, trabajaremos promoviendo la inversión, la innovación técnica y la generación constante de nuevos empleos.', 'Cuidad Inteligente\nEmprendedorismo\nGobierno Eficiente y Transparente\nProyectos Público Privados\nDistritos Creativos'),
	(7, 'Ninguno', '', '');
/*!40000 ALTER TABLE `eje_de_gobierno` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.eje_de_gobierno_por_proyecto
CREATE TABLE IF NOT EXISTS `eje_de_gobierno_por_proyecto` (
  `id_Proyecto` int(11) NOT NULL,
  `id_EjeDeGobierno` int(11) NOT NULL,
  PRIMARY KEY (`id_Proyecto`,`id_EjeDeGobierno`),
  KEY `FK_idEjeDeGobierno` (`id_EjeDeGobierno`),
  CONSTRAINT `FK_idEjeDeGobierno` FOREIGN KEY (`id_EjeDeGobierno`) REFERENCES `eje_de_gobierno` (`idEjeDeGobierno`),
  CONSTRAINT `FK_idProyecto_ejeDeGobierno` FOREIGN KEY (`id_Proyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.eje_de_gobierno_por_proyecto: ~23 rows (approximately)
/*!40000 ALTER TABLE `eje_de_gobierno_por_proyecto` DISABLE KEYS */;
INSERT INTO `eje_de_gobierno_por_proyecto` (`id_Proyecto`, `id_EjeDeGobierno`) VALUES
	(1, 6),
	(70, 6),
	(85, 6),
	(86, 6),
	(87, 6),
	(88, 6),
	(89, 6),
	(90, 6),
	(91, 6),
	(92, 6),
	(93, 3),
	(93, 4),
	(93, 5),
	(94, 3),
	(94, 4),
	(94, 5),
	(94, 6),
	(105, 3),
	(108, 6),
	(111, 6),
	(119, 6),
	(126, 6),
	(127, 6);
/*!40000 ALTER TABLE `eje_de_gobierno_por_proyecto` ENABLE KEYS */;

-- Dumping structure for table proyectos_ba_generated.poblacion_meta
CREATE TABLE IF NOT EXISTS `poblacion_meta` (
  `idPoblacionMeta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(512) NOT NULL,
  PRIMARY KEY (`idPoblacionMeta`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.poblacion_meta: ~14 rows (approximately)
/*!40000 ALTER TABLE `poblacion_meta` DISABLE KEYS */;
INSERT INTO `poblacion_meta` (`idPoblacionMeta`, `nombre`) VALUES
	(3, 'Adulto'),
	(4, 'Adulto Mayor'),
	(5, 'Ciudadano en General'),
	(6, 'Desempleado'),
	(7, 'Emprendedor'),
	(8, 'Empresa'),
	(9, 'Estudiante'),
	(10, 'Joven'),
	(11, 'Jubilado'),
	(12, 'Mujer'),
	(13, 'Niño'),
	(14, 'PyME'),
	(15, 'Trabajador'),
	(16, 'Turista');
/*!40000 ALTER TABLE `poblacion_meta` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.poblacion_meta_por_proyecto
CREATE TABLE IF NOT EXISTS `poblacion_meta_por_proyecto` (
  `idProyecto` int(11) NOT NULL,
  `idPoblacionMeta` int(11) NOT NULL,
  PRIMARY KEY (`idProyecto`,`idPoblacionMeta`),
  KEY `FK_idPoblacionMeta` (`idPoblacionMeta`),
  CONSTRAINT `FK_idPoblacionMeta` FOREIGN KEY (`idPoblacionMeta`) REFERENCES `poblacion_meta` (`idPoblacionMeta`),
  CONSTRAINT `FK_idProyecto_poblacionMeta` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.poblacion_meta_por_proyecto: ~19 rows (approximately)
/*!40000 ALTER TABLE `poblacion_meta_por_proyecto` DISABLE KEYS */;
INSERT INTO `poblacion_meta_por_proyecto` (`idProyecto`, `idPoblacionMeta`) VALUES
	(1, 3),
	(70, 3),
	(85, 3),
	(86, 3),
	(87, 3),
	(88, 3),
	(89, 3),
	(90, 3),
	(91, 3),
	(92, 3),
	(93, 4),
	(93, 6),
	(93, 9),
	(93, 10),
	(94, 3),
	(105, 3),
	(108, 3),
	(111, 3),
	(119, 3),
	(126, 3),
	(127, 3);
/*!40000 ALTER TABLE `poblacion_meta_por_proyecto` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.presupuesto_por_anio
CREATE TABLE IF NOT EXISTS `presupuesto_por_anio` (
  `idPresupuestoPoranio` int(11) NOT NULL AUTO_INCREMENT,
  `idProyecto` int(11) DEFAULT NULL,
  `anio` smallint(6) NOT NULL,
  `presupuesto` double NOT NULL,
  PRIMARY KEY (`idPresupuestoPoranio`),
  KEY `FK_idProyecto_presupuestoPorAnio` (`idProyecto`),
  CONSTRAINT `FK_idProyecto_presupuestoPorAnio` FOREIGN KEY (`idProyecto`) REFERENCES `proyecto` (`idProyecto`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.presupuesto_por_anio: ~61 rows (approximately)
/*!40000 ALTER TABLE `presupuesto_por_anio` DISABLE KEYS */;
INSERT INTO `presupuesto_por_anio` (`idPresupuestoPoranio`, `idProyecto`, `anio`, `presupuesto`) VALUES
	(1, 1, 2016, 1000),
	(29, 70, 2016, 1000),
	(30, 70, 2017, 2000),
	(57, 85, 2016, 20000),
	(58, 85, 2017, 30000),
	(59, 86, 2016, 20000),
	(60, 86, 2017, 30000),
	(61, 87, 2016, 20000),
	(62, 87, 2017, 30000),
	(63, 88, 2016, 20000),
	(64, 88, 2017, 30000),
	(65, 89, 2016, 20000),
	(66, 89, 2017, 30000),
	(67, 90, 2016, 1000),
	(68, 90, 2017, 2000),
	(69, 91, 2016, 1000),
	(70, 91, 2017, 2000),
	(71, NULL, 2016, 20000),
	(72, NULL, 2017, 20000),
	(73, NULL, 2016, 200),
	(74, NULL, 2017, 296),
	(77, NULL, 2016, 100),
	(78, NULL, 2017, 200),
	(83, NULL, 2016, 300),
	(84, NULL, 2017, 300),
	(85, NULL, 2018, 300),
	(86, NULL, 2016, 200),
	(87, NULL, 2017, 200),
	(88, NULL, 2016, 200),
	(89, NULL, 2017, 200),
	(90, 94, 2016, 2000),
	(91, 94, 2017, 3000),
	(92, 94, 2018, 120),
	(93, NULL, 2016, 1200),
	(94, NULL, 2017, 100),
	(95, NULL, 2018, 100),
	(96, NULL, 2019, 200),
	(97, NULL, 2016, 200),
	(98, NULL, 2017, 200),
	(99, NULL, 2018, 200),
	(100, NULL, 2019, 200),
	(101, NULL, 2020, 193),
	(102, NULL, 2016, 2000),
	(103, 105, 2016, 100),
	(104, 105, 2017, 200),
	(105, 105, 2018, 300),
	(106, NULL, 2016, 100),
	(107, NULL, 2017, 200),
	(108, NULL, 2018, 300),
	(109, NULL, 2019, 400),
	(110, NULL, 2020, 500),
	(111, NULL, 2016, 200),
	(112, 111, 2016, 100),
	(113, 111, 2017, 200),
	(114, 111, 2018, 300),
	(118, 111, 2016, 200),
	(119, 111, 2017, 300),
	(120, 111, 2018, 400),
	(121, 119, 2016, 100),
	(122, 119, 2017, 200),
	(123, 119, 2018, 300),
	(126, 126, 2018, 300),
	(127, 127, 2016, 200),
	(128, 127, 2017, 300);
/*!40000 ALTER TABLE `presupuesto_por_anio` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.rol: ~3 rows (approximately)
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`idRol`, `nombre`, `descripcion`) VALUES
	(1, 'admin', 'desc admin'),
	(2, 'user', 'desc user'),
	(3, 'admin2', 'desc admin2');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.rol_por_usuario
CREATE TABLE IF NOT EXISTS `rol_por_usuario` (
  `rol_idRol` int(11) NOT NULL,
  `usuario_idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`rol_idRol`,`usuario_idUsuario`),
  KEY `usuario_fk` (`usuario_idUsuario`),
  CONSTRAINT `rol_fk` FOREIGN KEY (`rol_idRol`) REFERENCES `rol` (`idRol`),
  CONSTRAINT `usuario_fk` FOREIGN KEY (`usuario_idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.rol_por_usuario: ~3 rows (approximately)
/*!40000 ALTER TABLE `rol_por_usuario` DISABLE KEYS */;
INSERT INTO `rol_por_usuario` (`rol_idRol`, `usuario_idUsuario`) VALUES
	(1, 1),
	(2, 1),
	(2, 2);
/*!40000 ALTER TABLE `rol_por_usuario` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  PRIMARY KEY (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.usuario: ~2 rows (approximately)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellido`, `email`, `descripcion`) VALUES
	(1, 'Federico', 'Bucich', 'fbucich@hexacta.com', 'User Fede Bucich'),
	(2, 'Esteban', 'Socas', 'esocas@hexacta.com', 'User Esteban socas');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.permiso_entidad
CREATE TABLE IF NOT EXISTS `permiso_entidad` (
  `idPermisoEntidad` int(11) NOT NULL AUTO_INCREMENT,
  `alta` tinyint(1) NOT NULL,
  `idRol` int(11) NOT NULL,
  `baja` tinyint(1) NOT NULL,
  `modificacion` tinyint(1) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idPermisoEntidad`),
  KEY `FK_idRol_pe` (`idRol`),
  CONSTRAINT `FK_idRol_pe` FOREIGN KEY (`idRol`) REFERENCES `rol` (`idRol`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.permiso_entidad: ~3 rows (approximately)
/*!40000 ALTER TABLE `permiso_entidad` DISABLE KEYS */;
INSERT INTO `permiso_entidad` (`idPermisoEntidad`, `alta`, `idRol`, `baja`, `modificacion`, `nombre`) VALUES
	(1, 1, 1, 1, 1, 'admin'),
	(2, 0, 2, 0, 0, 'jurisdiccion'),
	(3, 1, 2, 0, 1, 'proyecto');
/*!40000 ALTER TABLE `permiso_entidad` ENABLE KEYS */;


-- Dumping structure for table proyectos_ba_generated.usuario_por_jurisdiccion
CREATE TABLE IF NOT EXISTS `usuario_por_jurisdiccion` (
  `idUsuarioPorJurisdiccion` int(11) NOT NULL AUTO_INCREMENT,
  `idJurisdiccion` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`idUsuarioPorJurisdiccion`),
  KEY `FK_idJurisdiccion` (`idJurisdiccion`),
  KEY `FK_idUsuario_jurisdiccion` (`idUsuario`),
  CONSTRAINT `FK_idJurisdiccion` FOREIGN KEY (`idJurisdiccion`) REFERENCES `jurisdiccion` (`idJurisdiccion`),
  CONSTRAINT `FK_idUsuario_jurisdiccion` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table proyectos_ba_generated.usuario_por_jurisdiccion: ~0 rows (approximately)
/*!40000 ALTER TABLE `usuario_por_jurisdiccion` DISABLE KEYS */;
INSERT INTO `usuario_por_jurisdiccion` (`idUsuarioPorJurisdiccion`, `idJurisdiccion`, `idUsuario`) VALUES
	(1, 1, 1);
/*!40000 ALTER TABLE `usuario_por_jurisdiccion` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
