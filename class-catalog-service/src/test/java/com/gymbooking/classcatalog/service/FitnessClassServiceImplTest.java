package com.gymbooking.classcatalog.service;

import com.gymbooking.classcatalog.entities.FitnessClass;
import com.gymbooking.classcatalog.exception.BusinessRuleException;
import com.gymbooking.classcatalog.repository.FitnessClassRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FitnessClassServiceImplTest {

    //Creamos un imporstor (mock) del repositorio. No es el de verdad y no es necesario implementar sus metodos.
    @Mock
    private FitnessClassRepository fitnessClassRepository;


    //Creamos instancia real del servicio, pero le inyectamos el repositorio impostor
    @InjectMocks
    private FitnessClassServiceImpl fitnessClassService;

    @Test
    void whenFindById_thenReturnFitnessClass() throws BusinessRuleException {

        FitnessClass yoga = new FitnessClass();
        yoga.setId(1L);
        yoga.setName("Yoga");

        // Le decimos al repositorio "impostor" qué debe hacer:
        when(fitnessClassRepository.findById(1L)).thenReturn(Optional.of(yoga));

        //Ejecutamos el metodo que queremos probar
        FitnessClass found = fitnessClassService.getClassById(1L);

        //Comprobamos ls resultados
        Assertions.assertNotNull(found);
        Assertions.assertEquals("Yoga", found.getName());
    }
}
