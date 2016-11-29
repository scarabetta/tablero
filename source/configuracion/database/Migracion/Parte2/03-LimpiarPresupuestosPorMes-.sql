DELETE FROM presupuesto_proyecto_por_mes
where idPresupuestoProyectoPorMes in
(Select idPresupuestoProyectoPorMes from proyecto p 
LEFT JOIN 
(SELECT idPresupuestoProyectoPorMes, idProyecto, presupuesto, CONVERT(CONCAT(ppm.anio,'-', ppm.mes +1 ,'-','01'), DATE) AS FECHA from presupuesto_proyecto_por_mes ppm) PRESU
on PRESU.idProyecto = p.idProyecto
WHERE CONVERT(CONCAT(YEAR(p.FechaInicio),'-', MONTH(p.fechaInicio) ,'-','01'), DATE) > FECHA
OR CONVERT(CONCAT(YEAR(p.FechaFin),'-', MONTH(p.fechaFin) ,'-','01'), DATE) < FECHA)
AND presupuesto = 0;