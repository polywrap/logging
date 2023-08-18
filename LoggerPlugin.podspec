Pod::Spec.new do |s|
    s.name             = 'LoggerPlugin'
    s.version          = '0.0.1'
    s.summary          = 'Logger plugin'
    s.homepage         = 'https://github.com/polywrap/logging'
    s.license          = 'MIT'
    s.author           = { 'Cesar' => 'cesar@polywrap.io' }
  
    s.source_files = 'logger/implementations/swift/Source/**/*.swift'
    s.swift_version  = "5.0"
    s.ios.deployment_target = '14.0'
    s.source = { :git => "https://github.com/polywrap/logging.git", :branch => 'main' }
    s.static_framework = true
    s.dependency 'PolywrapClient', '~> 0.0.7'
end