package cl.duoc.semana5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import cl.duoc.semana5.repository.ProductoRepo;
import cl.duoc.semana5.model.Usuario;
import cl.duoc.semana5.repository.UsuarioRepo;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.seed.admin-password}")
    private String adminSeedPassword;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String adminUsername = "admin";
        String adminPassword = adminSeedPassword;

        if (usuarioRepo.findByUsername(adminUsername).isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            usuarioRepo.save(admin);
            System.out.println("Usuario 'admin' creado con contraseña hasheada");
        }

        if (productoRepo.count() == 0) {
            // Poblar productos iniciales de ejemplo: Sandwiches Chilenos (nombre, precio, descripción)
            productoRepo.save(new cl.duoc.semana5.model.Producto(0, "Chacarero", 5000, "Sandwich chileno con porotos verdes, carne y tomate"));
            productoRepo.save(new cl.duoc.semana5.model.Producto(0, "Barros Luco", 6000, "Sandwich chileno con carne y queso"));
            productoRepo.save(new cl.duoc.semana5.model.Producto(0, "Completo Italiano", 4000, "Sandwich chileno con palta, tomate y mayonesa"));
            productoRepo.save(new cl.duoc.semana5.model.Producto(0, "Completo Alemán", 4500, "Sandwich chileno con chucrut, salchicha y mayonesa"));
            productoRepo.save(new cl.duoc.semana5.model.Producto(0, "Lomito", 5500, "Sandwich chileno con lomo de cerdo, tomate y mayonesa"));
            productoRepo.save(new cl.duoc.semana5.model.Producto(0, "Italiano Especial", 5000, "Sandwich chileno con palta, tomate, mayonesa y ingredientes especiales"));
        }
    }

}
